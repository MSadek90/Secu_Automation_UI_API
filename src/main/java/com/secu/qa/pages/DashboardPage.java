package com.secu.qa.pages;

import com.secu.qa.routes.Routes;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

// projectSelect is always rendered in secu-backend resources/views/admin/home.blade.php
public final class DashboardPage extends BasePage {

    public DashboardPage(WebDriver driver, Duration timeout) {
        super(driver, timeout, By.id("projectSelect"));
    }

    @Step("Open the dashboard")
    public static DashboardPage open(WebDriver driver, String baseUrl, Duration timeout) {
        driver.get(baseUrl + Routes.ADMIN_DASHBOARD);
        return new DashboardPage(driver, timeout);
    }
}
