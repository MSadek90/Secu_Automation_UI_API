package com.secu.qa;

import com.secu.qa.api.ApiClient;
import com.secu.qa.api.AuthApi;
import com.secu.qa.driver.DriverFactory;
import com.secu.qa.routes.Routes;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.Map;

// Docs: JUnit 6.1.3 (BeforeEach, AfterEach), dotenv-java README, Selenium "Working with cookies"
public abstract class BaseTest {

    // a Windows environment variable wins over the local .env file; the file may be missing (CI)
    private static final Dotenv ENV = Dotenv.configure().ignoreIfMissing().load();

    protected WebDriver driver;
    protected Duration timeout;
    protected String baseUrl;
    protected ApiClient api;

    @RegisterExtension
    protected final ScreenshotOnFailure screenshots = new ScreenshotOnFailure(() -> driver);

    @BeforeEach
    protected void startBrowser() {
        baseUrl = requiredEnv("BASE_URL");
        timeout = Duration.ofSeconds(Long.parseLong(ENV.get("TIMEOUT_SECONDS", "10")));
        String browser = ENV.get("BROWSER", "chrome");
        boolean headless = Boolean.parseBoolean(ENV.get("HEADLESS", "false"));
        api = new ApiClient(baseUrl);
        driver = DriverFactory.create(browser, headless);
    }

    @AfterEach
    protected void quitBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    // the browser must be on the domain before a cookie can be added
    protected void loginAsAdminViaApi() {
        Map<String, String> cookies = new AuthApi(api)
                .loginAsAdmin(requiredEnv("ADMIN_EMAIL"), requiredEnv("ADMIN_PASSWORD"));
        driver.get(baseUrl + Routes.ADMIN_LOGIN);
        cookies.forEach((name, value) -> driver.manage().addCookie(new Cookie(name, value)));
    }

    protected static String requiredEnv(String name) {
        String value = ENV.get(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing setting " + name + ". Add it to .env (see .env.example)");
        }
        return value;
    }
}
