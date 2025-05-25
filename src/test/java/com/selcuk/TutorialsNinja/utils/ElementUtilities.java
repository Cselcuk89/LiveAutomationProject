package com.selcuk.TutorialsNinja.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException; // Explicit import
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ElementUtilities class provides a suite of static utility methods for interacting with
 * WebElements in Selenium. These methods encapsulate common Selenium actions,
 * adding logging and basic error handling to make test scripts cleaner and more robust.
 * All methods require a {@link WebDriver} instance to be passed as the first parameter
 * to perform operations on elements.
 */
public class ElementUtilities {
    private static final Logger log = LogManager.getLogger(ElementUtilities.class);

    /**
     * Retrieves the visible text from a list of WebElements.
     *
     * @param driver The WebDriver instance.
     * @param items  A list of {@link WebElement} from which to extract text.
     * @return A list of strings, where each string is the text of the corresponding WebElement.
     *         Returns an empty list if the input list is empty or if elements have no text.
     */
    public static List<String> getTextOfElements(WebDriver driver, List<WebElement> items) {
        log.info("Executing method: getTextOfElements for {} items", items.size());
        List<String> itemTexts = items.stream()
                                      .map(item -> getElementText(driver, item))
                                      .collect(Collectors.toList());
        log.info("Method getTextOfElements completed. Returning {} texts.", itemTexts.size());
        log.debug("Texts retrieved: {}", itemTexts);
        return itemTexts;
    }

    /**
     * Retrieves the visible text from a single WebElement.
     * Ensures the element is displayed before attempting to get its text.
     *
     * @param driver The WebDriver instance.
     * @param element The {@link WebElement} from which to get text.
     * @return The text of the element as a String. Returns an empty string if the element
     *         is not displayed or if an error occurs.
     */
    public static String getElementText(WebDriver driver, WebElement element) {
        String elementName = getElementNameForLogging(element);
        log.info("Executing method: getElementText for element: {}", elementName);
        String elementText = "";
        try {
            if (isElementDisplayed(driver, element)) { // Pass driver
                elementText = element.getText();
                log.debug("Text from element {}: '{}'", elementName, elementText);
            } else {
                log.warn("Element {} is not displayed, cannot get text.", elementName);
            }
        } catch (Exception e) {
            log.error("Exception getting text from element {}: {}", elementName, e.getMessage(), e);
        }
        log.info("Method getElementText for {} completed. Returning: '{}'", elementName, elementText);
        return elementText;
    }

    /**
     * Checks if a WebElement is displayed on the page.
     * Includes handling for {@link NoSuchElementException} if the element is not found.
     *
     * @param driver The WebDriver instance.
     * @param element The {@link WebElement} to check.
     * @return {@code true} if the element is displayed, {@code false} otherwise or if an error occurs.
     */
    public static boolean isElementDisplayed(WebDriver driver, WebElement element) {
        String elementName = getElementNameForLogging(element);
        log.info("Executing method: isElementDisplayed for element: {}", elementName);
        boolean isDisplayed = false;
        try {
            isDisplayed = element.isDisplayed();
            log.debug("Element {} display status: {}", elementName, isDisplayed);
        } catch (NoSuchElementException e) {
            log.warn("NoSuchElementException for element {}. Element not found, thus not displayed.", elementName);
            isDisplayed = false;
        } catch (Exception e) {
            log.error("Exception checking display status for element {}: {}", elementName, e.getMessage(), e);
            isDisplayed = false;
        }
        log.info("Method isElementDisplayed for {} completed. Returning: {}", elementName, isDisplayed);
        return isDisplayed;
    }

    /**
     * Checks if a WebElement is displayed on the page. This is similar to {@link #isElementDisplayed(WebDriver, WebElement)}
     * but might have slightly different logging or error handling nuances based on original intent.
     * Consider consolidating with {@link #isElementDisplayed(WebDriver, WebElement)} if behavior is identical.
     *
     * @param driver The WebDriver instance.
     * @param element The {@link WebElement} to check.
     * @return {@code true} if the element is displayed, {@code false} otherwise or if an error occurs.
     */
    public static boolean isElementDisplayedOnPage(WebDriver driver, WebElement element) {
        String elementName = getElementNameForLogging(element);
        log.info("Executing method: isElementDisplayedOnPage for element: {}", elementName);
        boolean isDisplayed = false;
        try {
            isDisplayed = element.isDisplayed();
            log.debug("Element {} (on page check) display status: {}", elementName, isDisplayed);
        } catch (Exception e) {
            log.error("Exception in isElementDisplayedOnPage for element {}: {}", elementName, e.getMessage(), e);
            isDisplayed = false;
        }
        log.info("Method isElementDisplayedOnPage for {} completed. Returning: {}", elementName, isDisplayed);
        return isDisplayed;
    }

    /**
     * Selects an option in a dropdown (HTML select tag) by its index.
     *
     * @param driver The WebDriver instance.
     * @param element The dropdown {@link WebElement} (select tag).
     * @param index The index of the option to select (0-based).
     */
    public static void selectOptionDropdownByIndex(WebDriver driver, WebElement element, int index) {
        String elementName = getElementNameForLogging(element);
        log.info("Executing method: selectOptionDropdownByIndex for element: {} with index: {}", elementName, index);
        try {
            if (isElementDisplayedOnPage(driver, element) && element.isEnabled()) {
                Select select = new Select(element);
                select.selectByIndex(index);
                log.debug("Selected option by index {} for dropdown {}", index, elementName);
            } else {
                log.warn("Dropdown {} is not displayed or not enabled. Cannot select option by index.", elementName);
            }
        } catch (Exception e) {
            log.error("Exception selecting option by index from dropdown {}: {}", elementName, e.getMessage(), e);
        }
        log.info("Method selectOptionDropdownByIndex for {} completed.", elementName);
    }

    /**
     * Selects an option in a dropdown (HTML select tag) by its visible text.
     *
     * @param driver The WebDriver instance.
     * @param element The dropdown {@link WebElement} (select tag).
     * @param option The visible text of the option to select.
     */
    public static void selectOptionDropdownByVisibleText(WebDriver driver, WebElement element, String option) {
        String elementName = getElementNameForLogging(element);
        log.info("Executing method: selectOptionDropdownByVisibleText for element: {} with option: '{}'", elementName, option);
        try {
            if (isElementDisplayedOnPage(driver, element) && element.isEnabled()) {
                Select select = new Select(element);
                select.selectByVisibleText(option);
                log.debug("Selected option by visible text '{}' for dropdown {}", option, elementName);
            } else {
                log.warn("Dropdown {} is not displayed or not enabled. Cannot select option by visible text.", elementName);
            }
        } catch (Exception e) {
            log.error("Exception selecting option by visible text from dropdown {}: {}", elementName, e.getMessage(), e);
        }
        log.info("Method selectOptionDropdownByVisibleText for {} completed.", elementName);
    }

    /**
     * Waits for a WebElement to become visible on the page for a specified duration.
     * Uses explicit wait (WebDriverWait).
     *
     * @param driver The WebDriver instance.
     * @param element The {@link WebElement} to wait for.
     * @param seconds The maximum time to wait, in seconds.
     */
    public static void waitForElement(WebDriver driver, WebElement element, int seconds) {
        String elementName = getElementNameForLogging(element);
        log.info("Executing method: waitForElement for element: {} with timeout: {} seconds", elementName, seconds);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
            wait.until(ExpectedConditions.visibilityOf(element));
            log.debug("Element {} is now visible.", elementName);
        } catch (Exception e) {
            log.error("Exception waiting for element {}: {}", elementName, e.getMessage(), e);
        }
        log.info("Method waitForElement for {} completed.", elementName);
    }

    /**
     * Waits for a WebElement to become visible and then checks its display status.
     *
     * @param driver The WebDriver instance.
     * @param element The {@link WebElement} to wait for and check.
     * @param seconds The maximum time to wait, in seconds.
     * @return {@code true} if the element is visible after waiting, {@code false} otherwise.
     */
    public static boolean waitAndCheckElementDisplayStatus(WebDriver driver, WebElement element, int seconds) {
        String elementName = getElementNameForLogging(element);
        log.info("Executing method: waitAndCheckElementDisplayStatus for element: {} with timeout: {} seconds", elementName, seconds);
        boolean isDisplayed = false;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
            wait.until(ExpectedConditions.visibilityOf(element));
            isDisplayed = element.isDisplayed();
            log.debug("Element {} display status after wait: {}", elementName, isDisplayed);
        } catch (Exception e) {
            log.warn("Exception or timeout in waitAndCheckElementDisplayStatus for element {}: {}. Element not displayed.", elementName, e.getMessage());
            isDisplayed = false;
        }
        log.info("Method waitAndCheckElementDisplayStatus for {} completed. Returning: {}", elementName, isDisplayed);
        return isDisplayed;
    }

    /**
     * Waits for a WebElement to become visible and then clicks on it.
     *
     * @param driver The WebDriver instance.
     * @param element The {@link WebElement} to wait for and click.
     * @param seconds The maximum time to wait for visibility, in seconds.
     */
    public static void waitForElementAndClick(WebDriver driver, WebElement element, int seconds) {
        String elementName = getElementNameForLogging(element);
        log.info("Executing method: waitForElementAndClick for element: {} with timeout: {} seconds", elementName, seconds);
        waitForElement(driver, element, seconds);
        clickOnElement(driver, element);
        log.info("Method waitForElementAndClick for {} completed.", elementName);
    }

    /**
     * Clicks on a WebElement if it is displayed and enabled.
     *
     * @param driver The WebDriver instance.
     * @param element The {@link WebElement} to click.
     */
    public static void clickOnElement(WebDriver driver, WebElement element) {
        String elementName = getElementNameForLogging(element);
        log.info("Executing method: clickOnElement for element: {}", elementName);
        try {
            if (isElementDisplayedOnPage(driver, element) && element.isEnabled()) {
                element.click();
                log.debug("Clicked on element: {}", elementName);
            } else {
                log.warn("Element {} is not displayed or not enabled. Cannot click.", elementName);
            }
        } catch (Exception e) {
            log.error("Exception clicking on element {}: {}", elementName, e.getMessage(), e);
        }
        log.info("Method clickOnElement for {} completed.", elementName);
    }

    /**
     * Attempts to click the first WebElement; if it's not displayed or found, attempts to click the second.
     *
     * @param driver The WebDriver instance.
     * @param elementOne The first {@link WebElement} to attempt to click.
     * @param elementTwo The second {@link WebElement} to click if the first is not available.
     */
    public static void clickEitherOfTheseElements(WebDriver driver, WebElement elementOne, WebElement elementTwo) {
        String nameOne = getElementNameForLogging(elementOne);
        String nameTwo = getElementNameForLogging(elementTwo);
        log.info("Executing method: clickEitherOfTheseElements for elements: {} OR {}", nameOne, nameTwo);
        try {
            if (isElementDisplayedOnPageWithoutException(driver, elementOne)) {
                log.debug("Element {} is displayed, clicking it.", nameOne);
                elementOne.click();
            } else {
                log.debug("Element {} was not displayed or found, attempting to click element {}.", nameOne, nameTwo);
                clickOnElement(driver, elementTwo);
            }
        } catch (Exception e) {
            log.error("Exception in clickEitherOfTheseElements for {} or {}: {}", nameOne, nameTwo, e.getMessage(), e);
        }
        log.info("Method clickEitherOfTheseElements completed.");
    }

    /**
     * Checks if a WebElement is displayed, specifically catching {@link NoSuchElementException}
     * and returning false if the element is not found.
     *
     * @param driver The WebDriver instance.
     * @param element The {@link WebElement} to check.
     * @return {@code true} if the element is displayed, {@code false} if not found or any other exception occurs.
     */
    public static boolean isElementDisplayedOnPageWithoutException(WebDriver driver, WebElement element) {
        String elementName = getElementNameForLogging(element);
        log.info("Executing method: isElementDisplayedOnPageWithoutException for element: {}", elementName);
        boolean isDisplayed = false;
        try {
            isDisplayed = element.isDisplayed();
            log.debug("Element {} (no exception check) display status: {}", elementName, isDisplayed);
        } catch (NoSuchElementException e) {
            log.warn("NoSuchElementException for element {} in isElementDisplayedOnPageWithoutException. Returning false.", elementName);
            isDisplayed = false;
        } catch (Exception e) {
            log.error("Generic exception for element {} in isElementDisplayedOnPageWithoutException: {}. Returning false.", elementName, e.getMessage(), e);
            isDisplayed = false;
        }
        log.info("Method isElementDisplayedOnPageWithoutException for {} completed. Returning: {}", elementName, isDisplayed);
        return isDisplayed;
    }

    /**
     * Returns a new {@link Actions} object for performing complex user gestures.
     *
     * @param driver The WebDriver instance to associate with the Actions class.
     * @return A new {@link Actions} object.
     */
    public static Actions getActions(WebDriver driver) {
        log.info("Executing method: getActions");
        Actions localActions = new Actions(driver);
        log.info("Method getActions completed. Returning new Actions instance.");
        return localActions;
    }

    /**
     * Simulates copying text using keyboard shortcuts (Ctrl+A for select all, Ctrl+C for copy).
     * This method assumes that the focus is already on an element or context where "select all" is applicable.
     *
     * @param driver The WebDriver instance.
     */
    public static void copyTextUsingKeyboards(WebDriver driver) {
        log.info("Executing method: copyTextUsingKeyboards");
        try {
            Actions localActions = getActions(driver);
            localActions.keyDown(Keys.CONTROL).sendKeys("a")
                    .keyUp(Keys.CONTROL)
                    .keyDown(Keys.CONTROL).sendKeys("c")
                    .keyUp(Keys.CONTROL).build().perform();
            log.debug("Performed copy (Ctrl+A, Ctrl+C) action.");
        } catch (Exception e) {
            log.error("Exception performing copyTextUsingKeyboards: {}", e.getMessage(), e);
        }
        log.info("Method copyTextUsingKeyboards completed.");
    }

    /**
     * Simulates pasting text into a WebElement using keyboard shortcuts (Ctrl+V).
     * Clicks on the element before attempting to paste.
     *
     * @param driver The WebDriver instance.
     * @param element The {@link WebElement} into which text should be pasted.
     */
    public static void pasteTextIntoFieldUsingKeyboardKeys(WebDriver driver, WebElement element) {
        String elementName = getElementNameForLogging(element);
        log.info("Executing method: pasteTextIntoFieldUsingKeyboardKeys for element: {}", elementName);
        try {
            Actions localActions = getActions(driver);
            localActions.click(element).keyDown(Keys.CONTROL).sendKeys("v")
                    .keyUp(Keys.CONTROL).build().perform();
            log.debug("Performed paste (Ctrl+V) action into element {}.", elementName);
        } catch (Exception e) {
            log.error("Exception performing pasteTextIntoFieldUsingKeyboardKeys for element {}: {}", elementName, e.getMessage(), e);
        }
        log.info("Method pasteTextIntoFieldUsingKeyboardKeys for {} completed.", elementName);
    }

    /**
     * Retrieves the value of a given DOM attribute of a WebElement.
     *
     * @param driver The WebDriver instance.
     * @param element The {@link WebElement} from which to get the attribute.
     * @param attributeName The name of the DOM attribute (e.g., "value", "placeholder").
     * @return The value of the attribute as a String, or an empty string if not found or an error occurs.
     */
    public static String getElementDomAttribute(WebDriver driver, WebElement element, String attributeName) {
        String elementName = getElementNameForLogging(element);
        log.info("Executing method: getElementDomAttribute for element: {} with attributeName: {}", elementName, attributeName);
        String attributeValue = "";
        try {
            attributeValue = element.getDomAttribute(attributeName);
            log.debug("DOM attribute '{}' for element {}: '{}'", attributeName, elementName, attributeValue);
        } catch (Exception e) {
            log.error("Exception getting DOM attribute '{}' from element {}: {}", attributeName, elementName, e.getMessage(), e);
        }
        log.info("Method getElementDomAttribute for {} completed. Returning: '{}'", elementName, attributeValue);
        return attributeValue;
    }

    /**
     * Retrieves the value of a given DOM property of a WebElement.
     *
     * @param driver The WebDriver instance.
     * @param element The {@link WebElement} from which to get the property.
     * @param propertyName The name of the DOM property (e.g., "value", "checked").
     * @return The value of the property as a String, or an empty string if not found or an error occurs.
     */
    public static String getElementDomProperty(WebDriver driver, WebElement element, String propertyName) {
        String elementName = getElementNameForLogging(element);
        log.info("Executing method: getElementDomProperty for element: {} with propertyName: {}", elementName, propertyName);
        String propertyValue = "";
        try {
            propertyValue = element.getDomProperty(propertyName);
            log.debug("DOM property '{}' for element {}: '{}'", propertyName, elementName, propertyValue);
        } catch (Exception e) {
            log.error("Exception getting DOM property '{}' from element {}: {}", propertyName, elementName, e.getMessage(), e);
        }
        log.info("Method getElementDomProperty for {} completed. Returning: '{}'", elementName, propertyValue);
        return propertyValue;
    }

    /**
     * Checks if a WebElement (e.g., checkbox, radio button) is selected.
     *
     * @param driver The WebDriver instance.
     * @param element The {@link WebElement} to check.
     * @return {@code true} if the element is selected, {@code false} otherwise or if not displayed/error occurs.
     */
    public static boolean isElementSelected(WebDriver driver, WebElement element) {
        String elementName = getElementNameForLogging(element);
        log.info("Executing method: isElementSelected for element: {}", elementName);
        boolean isSelected = false;
        try {
            if (isElementDisplayedOnPage(driver, element)) {
                isSelected = element.isSelected();
                log.debug("Element {} selected status: {}", elementName, isSelected);
            } else {
                log.warn("Element {} is not displayed. Cannot check if selected.", elementName);
            }
        } catch (Exception e) {
            log.error("Exception checking selected status for element {}: {}", elementName, e.getMessage(), e);
        }
        log.info("Method isElementSelected for {} completed. Returning: {}", elementName, isSelected);
        return isSelected;
    }

    /**
     * Retrieves the value of a given CSS property of a WebElement.
     *
     * @param driver The WebDriver instance.
     * @param element The {@link WebElement} from which to get the CSS value.
     * @param cssPropertyName The name of the CSS property (e.g., "color", "font-size").
     * @return The value of the CSS property as a String, or an empty string if not found or an error occurs.
     */
    public static String getElementCSSValue(WebDriver driver, WebElement element, String cssPropertyName) {
        String elementName = getElementNameForLogging(element);
        log.info("Executing method: getElementCSSValue for element: {} with cssPropertyName: {}", elementName, cssPropertyName);
        String cssValue = "";
        try {
            cssValue = element.getCssValue(cssPropertyName);
            log.debug("CSS property '{}' for element {}: '{}'", cssPropertyName, elementName, cssValue);
        } catch (Exception e) {
            log.error("Exception getting CSS property '{}' from element {}: {}", cssPropertyName, elementName, e.getMessage(), e);
        }
        log.info("Method getElementCSSValue for {} completed. Returning: '{}'", elementName, cssValue);
        return cssValue;
    }

    /**
     * Clears the text from an editable WebElement (e.g., input field, textarea).
     *
     * @param driver The WebDriver instance.
     * @param element The {@link WebElement} from which to clear text.
     */
    public static void clearTextFromElement(WebDriver driver, WebElement element) {
        String elementName = getElementNameForLogging(element);
        log.info("Executing method: clearTextFromElement for element: {}", elementName);
        try {
            if (isElementDisplayedOnPage(driver, element) && element.isEnabled()) {
                element.clear();
                log.debug("Cleared text from element: {}", elementName);
            } else {
                log.warn("Element {} is not displayed or not enabled. Cannot clear text.", elementName);
            }
        } catch (Exception e) {
            log.error("Exception clearing text from element {}: {}", elementName, e.getMessage(), e);
        }
        log.info("Method clearTextFromElement for {} completed.", elementName);
    }

    /**
     * Enters text into an editable WebElement. Clears the element before entering text.
     * Masks password text in logs.
     *
     * @param driver The WebDriver instance.
     * @param element The {@link WebElement} into which text should be entered.
     * @param text The text to enter into the element.
     */
    public static void enterTextIntoElement(WebDriver driver, WebElement element, String text) {
        String elementName = getElementNameForLogging(element);
        String logText = (elementName.toLowerCase().contains("password") || elementName.toLowerCase().contains("passwd")) ? "****" : text;
        log.info("Executing method: enterTextIntoElement for element: {} with text: '{}'", elementName, logText);
        try {
            if (isElementDisplayedOnPage(driver, element) && element.isEnabled()) {
                clearTextFromElement(driver, element);
                element.sendKeys(text);
                log.debug("Entered text into element: {}", elementName);
            } else {
                log.warn("Element {} is not displayed or not enabled. Cannot enter text.", elementName);
            }
        } catch (Exception e) {
            log.error("Exception entering text into element {}: {}", elementName, e.getMessage(), e);
        }
        log.info("Method enterTextIntoElement for {} completed.", elementName);
    }

    /**
     * Returns the number of WebElements in a list.
     * Handles null lists and potential exceptions when accessing list size.
     *
     * @param driver The WebDriver instance.
     * @param elements The list of {@link WebElement}s.
     * @return The count of elements in the list, or 0 if the list is null or an error occurs.
     */
    public static int getElementsCount(WebDriver driver, List<WebElement> elements) {
        log.info("Executing method: getElementsCount");
        int count = 0;
        if (elements != null) {
            try {
                count = elements.size();
                log.debug("Number of elements found: {}", count);
            } catch (Exception e) {
                log.warn("Exception getting elements count (elements list might be stale or invalid): {}", e.getMessage());
                count = 0;
            }
        } else {
            log.debug("Elements list is null. Count is 0.");
        }
        log.info("Method getElementsCount completed. Returning: {}", count);
        return count;
    }

    private static String getElementNameForLogging(WebElement element) {
        if (element == null) {
            return "null element";
        }
        // Attempt to get a meaningful identifier, fallback to toString
        try {
            String id = element.getAttribute("id");
            if (id != null && !id.isEmpty()) return "id:'" + id + "'";
            String name = element.getAttribute("name");
            if (name != null && !name.isEmpty()) return "name:'" + name + "'";
            String tagName = element.getTagName();
            if (tagName != null && !tagName.isEmpty()) return "tag:'" + tagName + "'";
        } catch (Exception e) {
            // Ignore if attributes can't be fetched (e.g., stale element)
        }
        return element.toString(); // Fallback
    }
}
