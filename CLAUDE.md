# CLAUDE.md

Rules for AI coding agents working in this repository (SECU UI + API test automation).

## Project
- Language and stack: Java, Selenium WebDriver, JUnit 5, REST Assured. Load testing is separate (k6, ADR-0003).
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
- `ExpectedConditions` is not used until its API is confirmed in the documentation (R-40).
- When a test is flaky, find the condition that was not yet true. Do not raise a timeout.

## Locators (R-28, R-29)
- Order: stable `id` / `name`, then CSS on a stable attribute (e.g. `data-test`), XPath only when nothing else works.
- No absolute XPath, no generated class names.
- Declare locators once, as private fields in the page or component.
- Verify a locator against the running application. Never infer it from what a form "usually" looks like.

## Structure
- `tests/` business steps and all assertions. `pages/` one class per page, `pages/components/` repeated parts, `utils/` `Actions` and `Waits`, `data/` test-data setup.
- Pages: no assertions (R-11), no exposed `WebElement` or `WebDriver` (R-09, R-10), services named in business language (R-20), return the next page or `this` (R-13).
- Each page has a static `open(...)` holding its URL; the constructor waits for a critical element of the page to be displayed (R-12, R-37, R-43); the title alone is not enough. Do not use `LoadableComponent`.
- `Actions` and `Waits` are used through instance fields created once in `BasePage` / `BaseComponent`. Pages never extend them (R-36). No hidden side effects in them (e.g. `type` must not press Enter).
- Money values are `BigDecimal` (R-19).
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
- TODO: fill in the build/test commands (all tests, single test) when the project is scaffolded.

## Still undecided (do not invent, ask)
- Exact method signatures of `Actions` and `Waits` (take them from the Java API docs; ask if not found).
- Which services to mock.

## Decided
- Reports: Allure (ADR-0004). No Cucumber/Gherkin (ADR-0005): tests are plain JUnit 5.
