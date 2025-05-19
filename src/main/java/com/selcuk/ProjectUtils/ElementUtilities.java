package com.selcuk.ProjectUtils;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ElementUtilities {
    public static WebDriver driver;
    static Actions actions;
    public static Select select;
    public ElementUtilities(WebDriver driver){
        this.driver = driver;
    }
    public static List<String> getTextOfElements(List<WebElement> items) {
        List<String> itemNames = new ArrayList<>();
        for (WebElement item : items){
            itemNames.add(getElementText(item));
        }
        return itemNames;
    }
    public static String getElementText(WebElement element){
        String elementTest = "";
        if (isElementDisplayed(element)){
            elementTest = element.getText();
        }
        return elementTest;
    }
    public static boolean isElementDisplayed(WebElement element) {
        boolean b = false;
        try {
            b = element.isDisplayed();
        } catch (NoSuchElementException e) {
            b = false;
        }
        return b;
    }
    public static boolean isElementDisplayedOnPage(WebElement element) {
        boolean b = false;
        b = element.isDisplayed();
        return b;
    }
    public static void selectOptionDropdownByIndex(WebElement element,int index){
        if (isElementDisplayedOnPage(element) && element.isEnabled()){
            select = new Select(element);
            select.selectByIndex(index);
        }
    }
    public static void selectOptionDropdownByVisibleText(WebElement element,String option){
        if (isElementDisplayedOnPage(element) && element.isEnabled()){
            select = new Select(element);
            select.selectByVisibleText(option);
        }
    }
    public static void waitForElement(WebElement element,int seconds){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    public static boolean waitAndCheckElementDisplayStatus(WebElement element, int seconds) {
        boolean b = false;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
            wait.until(ExpectedConditions.visibilityOf(element));
            b = true;
        }catch(Exception e) {
            b = false;
        }
        return b;
    }
    public static void waitForElementAndClick(WebElement element, int seconds) {
        waitForElement(element, seconds);
        clickOnElement(element);
    }
    public static void clickOnElement(WebElement element) {
        if (isElementDisplayedOnPage(element) && element.isEnabled()) {
            element.click();
        }
    }
    public static void clickEitherOfTheseElements(WebElement elementOne, WebElement elementTwo) {
        if(isElementDisplayedOnPageWithoutException(elementOne)) {
            elementOne.click();
        }else {
            elementTwo.click();
        }
    }
    public static boolean isElementDisplayedOnPageWithoutException(WebElement element) {
        boolean b = false;
        try {
            b = element.isDisplayed();
        } catch (NoSuchElementException e) {
            b = false;
        }
        return b;
    }
    public static Actions getActions(WebDriver driver) {
        actions = new Actions(driver);
        return actions;
    }
    public static void copyTextUsingKeyboards(WebDriver driver){
        actions = getActions(driver);
        actions.keyDown(Keys.CONTROL).sendKeys("a")
                .keyUp(Keys.CONTROL)
                .keyDown(Keys.CONTROL).sendKeys("c")
                .keyUp(Keys.CONTROL).build().perform();
    }
    public static void pasteTextIntoFieldUsingKeyboardKeys(WebElement element, WebDriver driver) {
        actions = getActions(driver);
        actions.click(element).keyDown(Keys.CONTROL).sendKeys("v")
                .keyUp(Keys.CONTROL).build().perform();
    }
    public static String getElementDomAttribute(WebElement element, String attributeName) {
        return element.getDomAttribute(attributeName);
    }
    public static String getElementDomProperty(WebElement element, String attributeName) {
        return element.getDomProperty(attributeName);
    }

    public static boolean isElementSelected(WebElement element) {
        boolean b = false;
        if (isElementDisplayedOnPage(element)) {
            b = element.isSelected();
        }
        return b;
    }
    public static String getElementCSSValue(WebElement element, String cssPropertyName) {
        String value = "";
        value = element.getCssValue(cssPropertyName);
        return value;
    }

    public static void clearTextFromElement(WebElement element) {
        if (isElementDisplayedOnPage(element) && element.isEnabled()) {
            element.clear();
        }
    }
    public static void enterTextIntoElement(WebElement element, String text) {
        if (isElementDisplayedOnPage(element) && element.isEnabled()) {
            clearTextFromElement(element);
            element.sendKeys(text);
        }
    }

    public static int getElementsCount(List<WebElement> elements) {

        int count = 0;

        try {
            count = elements.size();
        } catch (NoSuchElementException e) {
            count = 0;
        }

        return count;

    }








    }
