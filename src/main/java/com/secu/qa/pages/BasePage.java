package com.secu.qa.pages;

import com.secu.qa.utils.ActionBot;
import com.secu.qa.utils.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final Duration timeout;
    protected final Waits waits;
    protected final ActionBot bot;

    // every page waits for one of its own elements before it is used (R-43)
    protected BasePage(WebDriver driver, Duration timeout, By loadedWhenVisible) {
        this.driver = driver;
        this.timeout = timeout;
        this.waits = new Waits(driver, timeout);
        this.bot = new ActionBot(waits);
        waits.visible(loadedWhenVisible);
    }
}
