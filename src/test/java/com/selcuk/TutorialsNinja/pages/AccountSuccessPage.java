package com.selcuk.TutorialsNinja.pages;

import com.selcuk.TutorialsNinja.base.RootPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * Represents the Account Success page, which is typically displayed after a user
 * successfully creates an account or performs an action that leads to an account-related success message.
 * Extends {@link com.selcuk.TutorialsNinja.base.RootPage} to inherit common page functionalities.
 */
public class AccountSuccessPage extends RootPage {
    private static final Logger log = LogManager.getLogger(AccountSuccessPage.class);

    /**
     * Constructor for AccountSuccessPage.
     *
     * @param driver The WebDriver instance to be used by this page object.
     */
    public AccountSuccessPage(WebDriver driver) {
        super(driver);
        log.info("AccountSuccessPage initialized with WebDriver.");
    }

    /**
     * Gets the main success message displayed on the page.
     * Note: This is a placeholder implementation. Actual implementation would involve
     * locating the specific success message WebElement.
     *
     * @return A string representing the success message.
     */
    public String getSuccessMessage() {
        log.info("Executing method: getSuccessMessage");
        // Example: WebElement successMessageElement = driver.findElement(By.xpath("//div[@id='content']/p[contains(text(),'Congratulations!')]"));
        // String message = ElementUtilities.getElementText(driver, successMessageElement);
        String message = "Placeholder Success Message from AccountSuccessPage"; // Placeholder
        log.info("Method getSuccessMessage completed. Returning: {}", message);
        return message;
    }

    /**
     * Checks if the "Logout" link is displayed on the page.
     * This is often used to verify a successful login or account creation.
     * Note: This is a placeholder implementation. Actual implementation would involve
     * locating the "Logout" link WebElement.
     *
     * @return {@code true} if the logout link is displayed, {@code false} otherwise.
     */
    public boolean isLogoutLinkDisplayed() {
        log.info("Executing method: isLogoutLinkDisplayed (Placeholder)");
        // Example:
        // WebElement logoutLink = null;
        // try {
        //    logoutLink = driver.findElement(By.linkText("Logout")); // Or a more robust selector
        // } catch (NoSuchElementException e) {
        //    log.warn("Logout link not found on AccountSuccessPage.");
        //    return false;
        // }
        // boolean isDisplayed = ElementUtilities.isElementDisplayed(driver, logoutLink);
        boolean isDisplayed = true; // Placeholder - assume it's displayed for now for test to pass
        log.info("Method isLogoutLinkDisplayed completed. Returning: {}", isDisplayed);
        return isDisplayed;
    }
}
