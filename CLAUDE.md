# CLAUDE.md

Rules for AI coding agents working in this repository (SECU UI + API test automation).

## Project
- Language and stack: Java 23, Maven, Selenium WebDriver, JUnit 6, REST Assured, Allure. Load testing is separate (k6, ADR-0003).
- Root package: `com.secu.qa`. Framework code (driver, routes, utils, pages, api, data) lives in `src/main/java`; tests only in `src/test/java` (ADR-0006).
- Target: the **demo/test environment only**. Never run tests against Live/production.
- **Source of truth:** `docs/FRAMEWORK_RULES.md` (rules R-01 onward, each with its source). If this file and the rules file disagree, the rules file wins.
- If a rule does not exist for what you are about to do, **ask**. Do not invent a convention.

## Sources first (non-negotiable)
- Never write Selenium or REST Assured code from memory or by guessing. Every API call, wait, locator strategy and behavior must come from the current documentation or a runnable example you fetched in this session, or from an existing class in this repo.
- If you cannot find it in the documentation or the repo: **stop and ask the user.** Do not fill the gap with a plausible guess, a blog-post pattern, or "common practice".
- When you finish, list for each Selenium API you used the documentation page that supports it.

## Documentation
- Index: https://www.selenium.dev/llms.txt
- Reference: https://www.selenium.dev/documentation/
- Examples (executed against current releases): https://github.com/SeleniumHQ/seleniumhq.github.io/tree/trunk/examples
- Fetch the relevant page before using an API you are not certain about.
- Do not use the legacy (Selenium 2/3) or CDP pages as a basis for new code.
- If an API is not in the current documentation or API reference, it does not exist.
- The Selenium version is pinned in the build file. Do not guess it and do not change it.

## Drivers and browsers
- Selenium Manager handles drivers. Do not add a driver-manager dependency (e.g. WebDriverManager), do not download drivers, do not set a driver path.
- Configure browsers with that browser's `Options` class. No `DesiredCapabilities`, no raw capability maps.
- Headless is an argument: `--headless=new`.
- Timeouts and waits take `java.time.Duration`.

## Waiting
- Never `Thread.sleep` in a test or page.
- Explicit waits only (R-41). Never set an implicit wait; mixing them gives unpredictable wait times.
- Wait through the shared `Waits` utility (R-36, R-42), on the condition the next line depends on. Act after the wait returns, not inside the wait condition.
- One configurable timeout (default 10 s). Use `FluentWait` only when polling interval, ignored exceptions or message must be customized.
- `Waits` is built on `WebDriverWait` + `ExpectedConditions` (confirmed in the Selenium Java API). It already ignores `NotFoundException` while waiting; add other ignored exceptions only when a test needs it.
- When a test is flaky, find the condition that was not yet true. Do not raise a timeout.

## Locators (R-28, R-29)
- Order: stable `id` / `name`, then CSS on a stable attribute (e.g. `data-test`), XPath only when nothing else works.
- No absolute XPath, no generated class names.
- Declare locators once, as private fields in the page or component.
- Verify a locator against the running application. Never infer it from what a form "usually" looks like.

## Structure
- `src/test/java` holds only tests (business steps and all assertions); everything below is framework code in `src/main/java`. `driver/` `DriverFactory`, `routes/` `Routes` (all URL paths), `pages/` one class per page, `pages/components/` repeated parts, `utils/` `ActionBot` and `Waits`, `data/` test-data setup.
- Pages: no assertions (R-11), no exposed `WebElement` or `WebDriver` (R-09, R-10), services named in business language (R-20), return the next page or `this` (R-13).
- Each page has a static `open(...)` that navigates using a path from `routes/Routes` (R-37); never write a path inside a page or test; the constructor waits for a critical element of the page to be displayed (R-12, R-37, R-43); the title alone is not enough. Do not use `LoadableComponent`.
- `ActionBot` and `Waits` are used through instance fields created once in `BasePage` / `BaseComponent`. Pages never extend them (R-36). No hidden side effects in them (e.g. `type` must not press Enter).
- Money values are `BigDecimal` (R-19).
- SOLID (R-44, ADR-0008): one job per class; a class gets what it needs through its constructor or parameters and never reads settings or creates collaborators by itself; new behavior is a new class behind an interface, not an edit to a working class; interfaces stay small.
- Settings (R-45, ADR-0011): read only in `BaseTest` with `dotenv-java` (`ENV.get(...)`); a host environment variable wins over the local `.env` file, which is ignored by git and may be missing. Pass the values down. `BASE_URL`, `ADMIN_EMAIL`, `ADMIN_PASSWORD` are required; `BROWSER`, `HEADLESS`, `TIMEOUT_SECONDS` have defaults in code. Do not add settings classes. Never write real values into `.env.example`.
- Comments (R-46): one short line, only where the reason is not obvious. No block comments above classes, nothing that repeats the code. The one allowed exception is a one-line list of the documentation pages behind a Selenium call (R-40).
- Browsers come only from `DriverFactory` (R-38); never `new ChromeDriver()` in tests or pages. Chrome first; add others behind the factory only when asked.

## Tests
- One reason to exist per test, named after it (R-03). Set up data, act, then assert (R-02).
- Check first whether the behavior can be tested through the API instead of the browser (R-01).
- Test data and login are created through the application's HTTP routes with REST Assured (R-39) before the browser starts; the session cookie is added to the browser (R-04, R-21, R-22). Only login tests use the login UI.
- A test never depends on another test or on order. No `@Order` chaining (R-30).
- Each test owns the data it creates and cleans it up (R-25, R-26).
- One fresh `WebDriver` per test (`@BeforeEach`), always `quit()` in `@AfterEach`, never `close()`, never shared or global (R-27).
- No HTTP status-code assertions in UI tests (R-32). Downloads are fetched over HTTP, not through the browser (R-31).
- Console logs, JS errors and network interception use WebDriver BiDi, not the Chrome DevTools Protocol.

## Secrets and safety
- Never commit passwords, tokens, cookies or test OTP codes. Read them from environment variables / ignored config.
- Test OTP codes work only in the test environment and must never be valid in production (R-35).
- Do not log into third-party sites through the browser (R-33).

## Workflow
1. Open the feature under test and propose locators (throwaway script). Wait for review before writing the test.
2. Write the test following the rules above.
3. Run that one test, not the suite, and iterate until it passes.
4. Run it several times before calling it done. A test that passed once is not proven free of races.
5. Before finishing, check your diff for: sleeps, driver paths, absolute XPath, assertions inside pages, shared state.

## Commands
- All tests: `mvn test`
- One test class: `mvn test -Dtest=LoginTest`
- Compile only: `mvn test-compile`
- Report (after a run): `mvn allure:serve` opens it locally (Allure 2 report, see docs/RUN_NOTES.md). `mvn allure:report` builds a single file at `target/site/allure-maven-plugin/index.html`, which is what CI will use.
- Page and API methods carry `@Step("...")` (Allure); a failing UI test gets a screenshot attached by `ScreenshotOnFailure`, registered in `BaseTest`.

## Still undecided (do not invent, ask)
- Which services to mock.

## Decided
- Cleanup: through the application's HTTP delete routes, never the database (ADR-0007). Call delete once only: a second call on an already-deleted project restores it.
- Reports: Allure (ADR-0004). No Cucumber/Gherkin (ADR-0005): tests are plain JUnit 6.
