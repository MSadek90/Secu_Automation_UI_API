package com.secu.qa.tests.ui;

import com.secu.qa.BaseTest;
import com.secu.qa.pages.LoginPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginTest extends BaseTest {

    // passes when the dashboard loads; DashboardPage waits for its own element
    @Test
    void adminWithValidCredentialsReachesDashboard() {
        LoginPage.open(driver, baseUrl, timeout)
                .loginAs(requiredEnv("ADMIN_EMAIL"), requiredEnv("ADMIN_PASSWORD"));
    }

    // expected text from secu-backend lang/en/auth.php ('failed')
    @Test
    void wrongPasswordShowsError() {
        String error = LoginPage.open(driver, baseUrl, timeout)
                .loginAsExpectingError(requiredEnv("ADMIN_EMAIL"), "wrong-password")
                .errorMessage();
        assertEquals("These credentials do not match our records.", error);
    }
}
