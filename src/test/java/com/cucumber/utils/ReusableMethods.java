package com.cucumber.utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.cucumber.base.Base;

public class ReusableMethods extends Base {
    protected WebDriver driver;

    public ReusableMethods(WebDriver driver) {
        this.driver = driver;
    }

    public void clickElement(WebElement element) {
        element.click();
    }

    public void enterText(WebElement element, String text) {
        element.sendKeys(text);
    }
}