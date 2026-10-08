# Framework Rules

Rules every test and page class in this repository must follow. Each rule is taken from the official [Selenium documentation](https://www.selenium.dev/documentation/test_practices/) and links to its source. This file is the basis for the project rules given to AI coding agents.

Rules are added as each documentation page is studied. Items marked **Team decision** are not from the docs and are justified in an ADR or note.

---

## Test Design

| ID | Rule | Source |
|---|---|---|
| R-01 | Before writing a browser test, check whether the behavior can be verified at a lower level (API). Use the browser only when there is no alternative. | [Overview](https://www.selenium.dev/documentation/test_practices/overview/) |
| R-02 | Every test follows three steps: **set up data → perform actions → evaluate results**. Keep browser actions to one or two operations. | [Overview](https://www.selenium.dev/documentation/test_practices/overview/) |
| R-03 | Never script a full workflow as one test. Break it into independent, fast tests, each with **one reason to exist**. The test name states that reason. | [Overview](https://www.selenium.dev/documentation/test_practices/overview/) |
| R-04 | Create test data (users, records) through the API or database **before** the browser launches. The browser starts with the data already "in hand". | [Overview](https://www.selenium.dev/documentation/test_practices/overview/) |
| R-05 | Tests are written in user/business language. No buttons, fields, locators or browser controls inside test methods. | [Overview](https://www.selenium.dev/documentation/test_practices/overview/) |
| R-06 | Do not automate UI that will change considerably in the near future. Under a tight deadline with no existing automation, test manually first. | [Overview](https://www.selenium.dev/documentation/test_practices/overview/) |
| R-07 | Do not use Selenium for performance testing. Use the dedicated tool (k6, ADR-0003). | [Discouraged: Performance testing](https://www.selenium.dev/documentation/test_practices/discouraged/performance_testing/) |

## Page Objects

| ID | Rule | Source |
|---|---|---|
| R-08 | One Page Object per page (or part of a page). It is the **only** place in the suite that knows the HTML structure of that page. Locators are defined once, as private fields. | [Page Object Models](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/) |
| R-09 | Public methods represent the **services** the page offers (e.g. `loginAs`, `composeMail`), not its mechanics. Do not expose internals such as `WebElement`s. | [POM: Summary](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/) |
| R-10 | Page Objects do not expose the `WebDriver` instance (no `getDriver()`). | [POM: Implementation Notes](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/) |
| R-11 | **No assertions in Page Objects.** Pages return data (`String`, `boolean`, values); tests make all assertions. | [POM: Assertions in Page Objects](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/) |
| R-12 | The single exception: the constructor verifies the expected page (and its critical elements) is loaded, and throws if not. | [POM: Assertions in Page Objects](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/) |
| R-13 | An action that navigates returns the Page Object of the destination. An action that stays on the page returns `this` (enabling fluent chaining). Queries return data. | [POM: Implementation Notes](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/), [Fluent API](https://www.selenium.dev/documentation/test_practices/encouraged/consider_using_a_fluent_api/) |
| R-14 | When the same action can have different outcomes, model each outcome as a separate method (`loginAs` / `loginAsExpectingError`). | [POM: Summary](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/) |
| R-15 | Repeated or shared sections (cards, table rows, navigation bar) are **Page Component Objects**. A component receives its root element and locates only within it (`root.findElement`). | [POM: Page Component Objects](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/) |
| R-16 | Larger services are composed from smaller ones; steps are never duplicated inside a page (`loginAs` calls `typeUsername`, `typePassword`, `submitLogin`). | [POM: Example](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/) |

## Code Conventions

| ID | Rule | Source |
|---|---|---|
| R-17 | Pages and components receive their dependency through the constructor (`WebDriver` for pages, root `WebElement` for components). Shared setup lives in `BasePage` / `BaseComponent`. | [POM: Page Component Objects](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/) (examples) |
| R-18 | Locators inside a component must match **every** instance of that component, never one specific instance (e.g. not `By.id("add-to-cart-backpack")`). | **Team decision** — the docs example hard-codes one product's ID and fails for others |
| R-19 | Monetary values are parsed as `BigDecimal`, never `double`. | [POM: Page Component Objects](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/) (example) |

## Test Language & Setup

| ID | Rule | Source |
|---|---|---|
| R-20 | Methods are named in the business domain's language (ubiquitous language): `createNewAccount()`, not `fillTableAndSubmit()`. Tests read as what the user wants to DO and KNOW, never HOW the UI does it. | [Domain Specific Language](https://www.selenium.dev/documentation/test_practices/encouraged/domain_specific_language/) |
| R-21 | Selenium is never used to prepare a test. Repetitive setup and test data are created through APIs (or the database) before the browser launches. | [Generating Application State](https://www.selenium.dev/documentation/test_practices/encouraged/generating_application_state/) |
| R-22 | Authentication is done via API and a session cookie is set in the browser, instead of logging in through the UI before every test. Exception: tests of the login feature itself. | [Generating Application State](https://www.selenium.dev/documentation/test_practices/encouraged/generating_application_state/) (exception: **Team decision**) |

## External Dependencies

| ID | Rule | Source |
|---|---|---|
| R-23 | External services (payment, email, SMS, maps, third-party APIs) are replaced by mocks in functional tests. A small set of integration tests still runs against the real service (or its sandbox) to verify the integration itself. | [Mock External Services](https://www.selenium.dev/documentation/test_practices/encouraged/mock_external_services/) (integration tests: **Team decision**) |

## Reporting

| ID | Rule | Source |
|---|---|---|
| R-24 | Reports come from the test framework, not Selenium: JUnit-XML output for the CI server, plus a human-readable HTML report. Page-object services are reported as named steps, and a screenshot is attached on failure. | [Improved Reporting](https://www.selenium.dev/documentation/test_practices/encouraged/improved_reporting/) (steps & screenshots: **Team decision**) |

## Test Isolation

| ID | Rule | Source |
|---|---|---|
| R-25 | Tests never share test data. Each test creates (or exclusively owns) the records it acts on; no test picks "any existing record" from the database. | [Avoid Sharing State](https://www.selenium.dev/documentation/test_practices/encouraged/avoid_sharing_state/) |
| R-26 | Each test cleans up the data it created; stale data left by failed runs is cleaned before execution. | [Avoid Sharing State](https://www.selenium.dev/documentation/test_practices/encouraged/avoid_sharing_state/) |
| R-27 | A new `WebDriver` instance is created for every test (`@BeforeEach`) and always quit after it, pass or fail (`@AfterEach`). Each instance uses the driver's fresh temporary profile; never a personal or persisted profile (`--user-data-dir`). | [Avoid Sharing State](https://www.selenium.dev/documentation/test_practices/encouraged/avoid_sharing_state/), [Fresh Browser per Test](https://www.selenium.dev/documentation/test_practices/encouraged/fresh_browser_per_test/) |
| R-30 | Tests never depend on another test having run, its result, or execution order. When a feature consumes data produced elsewhere after an async sync, the consumer test creates its own stub state; the producer is covered by a separate test. Verifying the sync itself is a dedicated integration test with a bounded wait. | [Test Independency](https://www.selenium.dev/documentation/test_practices/encouraged/test_independency/) (sync test: **Team decision**) |

## Locators

| ID | Rule | Source |
|---|---|---|
| R-28 | Locator priority: **ID** (only if unique and stable — never auto-generated) → **CSS selector** → **XPath** only when nothing else works. `linkText` only for links. `tagName` only with `findElements` or inside a component root where it is unique. | [Locators](https://www.selenium.dev/documentation/test_practices/encouraged/locators/) |
| R-29 | Locators are short and readable (no absolute XPath). Narrow the search scope: locate a container once and search within it. | [Locators](https://www.selenium.dev/documentation/test_practices/encouraged/locators/) |

---

## Pending (decided after the related page is studied)

- Page-loaded check: page title vs. waiting for a critical element → after **Waits**
- Reporting tool (Allure proposed) → ADR-0004
- Which external services to mock, and the mocking tool → during framework design
- Exact test data setup mechanism (available APIs, cookie-based login) → when the `data/` layer is designed
