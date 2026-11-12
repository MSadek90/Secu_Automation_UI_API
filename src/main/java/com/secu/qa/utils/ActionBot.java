package com.secu.qa.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

// Docs: Selenium Java API (WebElement: click, clear, sendKeys, getText) and the Interacting with web elements page
public final class ActionBot {

    private final Waits waits;

    public ActionBot(Waits waits) {
        this.waits = waits;
    }

    public void click(By locator) {
        waits.clickable(locator).click();
    }

    public void type(By locator, String text) {
        WebElement field = waits.visible(locator);
        field.clear();
        field.sendKeys(text);
    }

    public String text(By locator) {
        return waits.visible(locator).getText();
    }
}
