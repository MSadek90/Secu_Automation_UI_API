package com.secu.qa;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.util.function.Supplier;

// Docs: Allure "JUnit 5 and Selenium screenshots" guide; JUnit runs this before @AfterEach closes the browser
public final class ScreenshotOnFailure implements TestExecutionExceptionHandler {

    private final Supplier<WebDriver> driver;

    public ScreenshotOnFailure(Supplier<WebDriver> driver) {
        this.driver = driver;
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            byte[] png = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
            Allure.attachment("Screenshot on failure", new ByteArrayInputStream(png));
        }
        throw throwable;
    }
}
