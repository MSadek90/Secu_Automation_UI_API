# Run Notes

Problems found while running the tests, and how we handled them.

## 1. Login button covered (2026-10-12)

**Problem:** clicking **Sign in** fails with `ElementClickInterceptedException`.

**Cause:** the demo shows the Laravel Debugbar, a bar fixed at the bottom of the window. In a fresh test browser it sits on top of the button.

**Options:**
- JavaScript click: works, but it clicks through anything covering the button, so it could hide a real bug.
- Zoom out to 80% (`--force-device-scale-factor=0.8` in `DriverFactory`): the page gets smaller and the button moves above the bar.

**Chosen:** zoom out. It works: both login tests pass.

## 2. API login refused (2026-10-13)

**Problem:** logging in through the API fails with `Connection refused`.

**Cause:** REST Assured's `csrf(...)` fetches the form page in a separate GET that does not use the request's `baseUri`, so it went to the default local address.

**Fix:** give `csrf` the full URL (`baseUrl + path`) in `ApiClient`. `DashboardTest` passes.

## 3. Empty Allure report with `allure:serve` (2026-10-13)

**Problem:** `mvn allure:serve` opened a report with 0 tests.

**Cause:** not known. The tests were recorded (`target/allure-results` has 3 results with their steps) and the generated report folder shows 3 tests, but the page served by `allure:serve` loads data with 0 tests.

**Fix:** switched the report to Allure 2 (`<reportVersion>2.39.0</reportVersion>` in the `allure-maven` plugin). `mvn allure:serve` now opens the report with all tests. In CI the report is generated with `allure:report`, not served.

## 4. Login button covered again in CI (2026-11-12)

**Problem:** in GitHub Actions both login tests failed with `ElementClickInterceptedException`, the same bar as note 1.

**Cause:** in headless mode the browser window is short. Even at 80% the button stayed under the Debugbar. On a local machine the window takes the screen size, so it was not seen there.

**Fix:** `DriverFactory` sets the window to 1920x1080 in headless mode with `driver.manage().window().setSize(...)`, after the driver is created. All 3 tests pass in CI.

**Note:** the size must be set after `new ChromeDriver(options)`, and every option must be added before it. One run created the driver first, so Chrome started without `--headless=new` and exited (`session not created`).
