package com.secu.qa.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Locale;

// Docs: Selenium Java API (ChromeOptions, ChromiumOptions, ChromeDriver), the Chrome page for --headless=new, Chromium ui/display/display.cc for --force-device-scale-factor
public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver create(String browser, boolean headless) {
        return switch (browser.toLowerCase(Locale.ROOT)) {
            case "chrome" -> chrome(headless);
            default -> throw new IllegalArgumentException(
                    "Unsupported browser '" + browser + "'. Supported: chrome");
        };
    }

    private static WebDriver chrome(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        // 80% scale keeps buttons near the bottom clear of a fixed bar on the demo pages
        options.addArguments("--force-device-scale-factor=0.8");
        if (headless) {
            options.addArguments("--headless=new");
        }
        return new ChromeDriver(options);
    }
}
