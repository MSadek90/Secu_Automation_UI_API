package com.secu.qa.pages;

import com.secu.qa.routes.Routes;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

// Locators from secu-backend resources/views/admin/auth/login.blade.php
public final class LoginPage extends BasePage {

    private final By emailField = By.id("email");
    private final By passwordField = By.id("password");
    private final By submitButton = By.cssSelector("button[type='submit']");
    private final By errorMessage = By.cssSelector("[role='alert']");

    public LoginPage(WebDriver driver, Duration timeout) {
        super(driver, timeout, By.id("email"));
    }

    @Step("Open the login page")
    public static LoginPage open(WebDriver driver, String baseUrl, Duration timeout) {
        driver.get(baseUrl + Routes.ADMIN_LOGIN);
        return new LoginPage(driver, timeout);
    }

    @Step("Log in and expect the dashboard")
    public DashboardPage loginAs(String email, String password) {
        submit(email, password);
        return new DashboardPage(driver, timeout);
    }

    @Step("Log in and expect an error")
    public LoginPage loginAsExpectingError(String email, String password) {
        submit(email, password);
        return new LoginPage(driver, timeout);
    }

    @Step("Read the error message")
    public String errorMessage() {
        return bot.text(errorMessage);
    }

    private void submit(String email, String password) {
        bot.type(emailField, email);
        bot.type(passwordField, password);
        bot.click(submitButton);
    }
}
