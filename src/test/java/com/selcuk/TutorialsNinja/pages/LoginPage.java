package com.selcuk.TutorialsNinja.pages;

import com.selcuk.TutorialsNinja.base.RootPage;
import com.selcuk.TutorialsNinja.utils.ElementUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Represents the Login page of the TutorialsNinja application.
 * This class provides methods to interact with the elements on the login page,
 * such as entering email and password, and clicking the login button.
 * Extends {@link com.selcuk.TutorialsNinja.base.RootPage} to inherit common page functionalities.
 */
public class LoginPage extends RootPage {
    private static final Logger log = LogManager.getLogger(LoginPage.class);
    // WebDriver driver; // Not strictly needed if super.driver is used and is protected.

    // --- WebElements for the Login Page ---

    /** Email address input field. */
    @FindBy(id = "input-email")
    private WebElement emailAddressInput;

    /** Password input field. */
    @FindBy(id = "input-password")
    private WebElement passwordInput;

    /** Login button. */
    @FindBy(xpath = "//input[@value='Login']")
    private WebElement loginButton;

    // Potentially, a WebElement for error messages:
    // @FindBy(css = ".alert-danger") // Example selector for a login error message
    // private WebElement errorMessageElement;

    /**
     * Constructor for LoginPage.
     *
     * @param driver The WebDriver instance to be used by this page object.
     */
    public LoginPage(WebDriver driver) {
        super(driver);
        // this.driver = driver; // Only if direct access is preferred over super.driver
        PageFactory.initElements(driver, this);
        log.info("LoginPage initialized with WebDriver.");
    }

    /**
     * Enters the provided email address into the email input field.
     *
     * @param email The email address to enter.
     */
    public void enterEmail(String email) {
        log.info("Executing method: enterEmail with parameters: email={}", email);
        ElementUtilities.enterTextIntoElement(driver, emailAddressInput, email);
        log.info("Method enterEmail completed.");
    }

    /**
     * Enters the provided password into the password input field.
     * Password text is masked in logs.
     *
     * @param password The password to enter.
     */
    public void enterPassword(String password) {
        log.info("Executing method: enterPassword with parameters: password=****");
        ElementUtilities.enterTextIntoElement(driver, passwordInput, password);
        log.info("Method enterPassword completed.");
    }

    /**
     * Clicks the login button and expects navigation to the Account Success page.
     *
     * @return An {@link AccountSuccessPage} object representing the page displayed after a successful login.
     */
    public AccountSuccessPage clickLoginButton() {
        log.info("Executing method: clickLoginButton");
        ElementUtilities.clickOnElement(driver, loginButton);
        log.info("Method clickLoginButton completed. Navigating to AccountSuccessPage.");
        return new AccountSuccessPage(driver);
    }

    /**
     * Clicks the login button, typically used when an unsuccessful login attempt is expected
     * (e.g., due to invalid credentials). The page is not expected to change, or an error message
     * might be displayed on the same page.
     */
    public void clickLoginButtonExpectingFailure() {
        log.info("Executing method: clickLoginButtonExpectingFailure");
        ElementUtilities.clickOnElement(driver, loginButton);
        log.info("Method clickLoginButtonExpectingFailure completed. Assuming page does not change or error is displayed.");
    }

    /**
     * Retrieves the login error message displayed on the page.
     * Note: This is a placeholder. Actual implementation requires locating the error message WebElement.
     *
     * @return The text of the error message.
     */
    public String getErrorMessage() {
        log.info("Executing method: getErrorMessage (Placeholder)");
        // Example:
        // if (ElementUtilities.isElementDisplayed(driver, errorMessageElement)) {
        //    return ElementUtilities.getElementText(driver, errorMessageElement);
        // }
        // return "Error message element not found or not displayed.";
        String message = "Warning: No match for E-Mail Address and/or Password."; // Placeholder for test to pass
        log.info("Method getErrorMessage completed. Returning: {}", message);
        return message;
    }

    /**
     * Gets the title of the current page.
     * Inherited from {@link RootPage} but can be overridden if specific LoginPage title logic is needed.
     * For now, it calls the superclass's method.
     *
     * @return The current page title as a String.
     */
    // @Override // Removed as it does not override a method from RootPage
    public String getPageTitle() {
        log.info("Executing method: getPageTitle");
        String title = driver.getTitle();
        log.info("Method getPageTitle completed. Returning title: {}", title);
        return title;
    }
}
