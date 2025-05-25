package com.selcuk.TutorialsNinja.pages;

import com.selcuk.TutorialsNinja.base.RootPage;
import com.selcuk.TutorialsNinja.utils.ElementUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

/**
 * Represents the Account Registration page of the TutorialsNinja application.
 * This class provides methods to interact with the registration form elements,
 * such as inputting personal details, password, newsletter subscription, and submitting the form.
 * Extends {@link com.selcuk.TutorialsNinja.base.RootPage} to inherit common page functionalities.
 */
public class RegisterPage extends RootPage {
    private static final Logger log = LogManager.getLogger(RegisterPage.class);

    // --- WebElements for the Registration Form ---

    /** First name input field. */
    @FindBy(how = How.ID, using = "input-firstname")
    private WebElement firstNameField;

    /** Last name input field. */
    @FindBy(how = How.ID,using = "input-lastname")
    private WebElement lastNameField;

    /** Email input field. */
    @FindBy(how = How.ID,using = "input-email")
    private WebElement emailField;

    /** Telephone input field. */
    @FindBy(how = How.ID,using = "input-telephone")
    private WebElement telephoneField;

    /** Password input field. */
    @FindBy(how = How.ID,using = "input-password")
    private WebElement passwordField;

    /** Password confirmation input field. */
    @FindBy(how = How.ID,using = "input-confirm")
    private WebElement passwordConfirmField;

    /** Privacy Policy agreement checkbox. */
    @FindBy(how = How.NAME,using = "agree")
    private WebElement privacyPolicyField;

    /** Radio button for 'Yes' to newsletter subscription. */
    @FindBy(how = How.XPATH,using = "//input[@name='newsletter'][@value='1']")
    private WebElement yesNewsletterOption;

    /** Radio button for 'No' to newsletter subscription. */
    @FindBy(how = How.XPATH,using = "//input[@name='newsletter'][@value='0']")
    private WebElement noNewsletterOption;

    /** Continue button to submit the registration form. */
    @FindBy(how = How.XPATH,using = "//input[@value='Continue']")
    private WebElement continueButton;

    // --- WebElements for Warning Messages ---

    /** Warning message for first name field validation. */
    @FindBy(how = How.XPATH,using = "//input[@id='input-firstname']/following-sibling::div")
    private WebElement firstNameWarning;

    /** Warning message for last name field validation. */
    @FindBy(how = How.XPATH,using = "//input[@id='input-lastname']/following-sibling::div")
    private WebElement lastNameWarning;

    /** Warning message for email field validation. */
    @FindBy(how = How.XPATH,using = "//input[@id='input-email']/following-sibling::div")
    private WebElement emailWarning;

    /** Warning message for telephone field validation. */
    @FindBy(how = How.XPATH,using = "//input[@id='input-telephone']/following-sibling::div")
    private WebElement telephoneWarning;

    /** Warning message for password field validation. */
    @FindBy(how = How.XPATH,using = "//input[@id='input-password']/following-sibling::div")
    private WebElement passwordWarning;

    /** Warning message for password confirmation field validation. */
    @FindBy(how = How.XPATH,using = "//input[@id='input-confirm']/following-sibling::div")
    private WebElement passwordConfirmationWarning;

    // --- WebElements for Breadcrumbs and Labels ---

    /** Breadcrumb link for the 'Register' page. */
    @FindBy(how = How.XPATH,using = "//ul[@class='breadcrumb']//a[text()='Register']")
    private WebElement registerPageBreadcrumb;

    /** Label for the first name input field. */
    @FindBy(how = How.CSS,using = "label[for='input-firstname']")
    private WebElement firstNameFieldLabel;

    /** Label for the last name input field. */
    @FindBy(how = How.CSS,using ="label[for='input-lastname']")
    private WebElement lastNameFieldLabel;

    /** Label for the email input field. */
    @FindBy(how = How.CSS,using ="label[for='input-email']")
    private WebElement emailFieldLabel;

    /** Label for the telephone input field. */
    @FindBy(how = How.CSS,using ="label[for='input-telephone']")
    private WebElement telephoneFieldLabel;

    /** Label for the password input field. */
    @FindBy(how = How.CSS,using ="label[for='input-password']")
    private WebElement passwordFieldLabel;

    /** Label for the password confirmation input field. */
    @FindBy(how = How.CSS,using ="label[for='input-confirm']")
    private WebElement passwordConfirmFieldLabel;

    /** Label for the Privacy Policy checkbox. */
    @FindBy(how = How.CSS,using ="div[class='pull-right']")
    private WebElement privacyPolicyFieldLabel;

    /** Link to the login page, often found on the registration page. */
    @FindBy(how = How.LINK_TEXT, using = "login page")
    private WebElement loginPageOption;


    /**
     * Constructor for RegisterPage.
     *
     * @param driver The WebDriver instance to be used by this page object.
     */
    public RegisterPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        log.info("RegisterPage initialized with WebDriver.");
    }

    // --- Action Methods ---

    /**
     * Enters the first name into the registration form.
     * @param firstNameText The first name to enter.
     */
    public void enterFirstName(String firstNameText) {
        log.info("Executing method: enterFirstName with parameters: firstNameText={}", firstNameText);
        ElementUtilities.enterTextIntoElement(driver, firstNameField, firstNameText);
        log.info("Method enterFirstName completed.");
    }

    /**
     * Enters the last name into the registration form.
     * @param lastNameText The last name to enter.
     */
    public void enterLastName(String lastNameText) {
        log.info("Executing method: enterLastName with parameters: lastNameText={}", lastNameText);
        ElementUtilities.enterTextIntoElement(driver, lastNameField, lastNameText);
        log.info("Method enterLastName completed.");
    }

    /**
     * Enters the email address into the registration form.
     * @param emailText The email address to enter.
     */
    public void enterEmail(String emailText) {
        log.info("Executing method: enterEmail with parameters: emailText={}", emailText);
        ElementUtilities.enterTextIntoElement(driver, emailField, emailText);
        log.info("Method enterEmail completed.");
    }

    /**
     * Enters the telephone number into the registration form.
     * @param telephoneText The telephone number to enter.
     */
    public void enterTelephone(String telephoneText) {
        log.info("Executing method: enterTelephone with parameters: telephoneText={}", telephoneText);
        ElementUtilities.enterTextIntoElement(driver, telephoneField, telephoneText);
        log.info("Method enterTelephone completed.");
    }

    /**
     * Enters the password into the password field.
     * Password text is masked in logs.
     * @param passwordText The password to enter.
     */
    public void enterPassword(String passwordText) {
        log.info("Executing method: enterPassword with parameters: passwordText=****");
        ElementUtilities.enterTextIntoElement(driver, passwordField, passwordText);
        log.info("Method enterPassword completed.");
    }

    /**
     * Enters the password into the password confirmation field.
     * Password text is masked in logs.
     * @param passwordText The password to confirm.
     */
    public void enterConfirmationPassword(String passwordText) {
        log.info("Executing method: enterConfirmationPassword with parameters: passwordText=****");
        ElementUtilities.enterTextIntoElement(driver, passwordConfirmField, passwordText);
        log.info("Method enterConfirmationPassword completed.");
    }

    /**
     * Selects the Privacy Policy checkbox.
     */
    public void selectPrivacyPolicyField() {
        log.info("Executing method: selectPrivacyPolicyField");
        ElementUtilities.clickOnElement(driver, privacyPolicyField);
        log.info("Method selectPrivacyPolicyField completed.");
    }

    /**
     * Selects the 'Yes' option for newsletter subscription.
     */
    public void selectYesNewsletterOption() {
        log.info("Executing method: selectYesNewsletterOption");
        ElementUtilities.clickOnElement(driver, yesNewsletterOption);
        log.info("Method selectYesNewsletterOption completed.");
    }

    /**
     * Selects the 'No' option for newsletter subscription.
     */
    public void selectNoNewletterOption() {
        log.info("Executing method: selectNoNewletterOption");
        ElementUtilities.clickOnElement(driver, noNewsletterOption);
        log.info("Method selectNoNewletterOption completed.");
    }

    /**
     * Clicks the 'Continue' button to submit the registration form.
     * Expects navigation to the {@link AccountSuccessPage} on successful registration.
     *
     * @return An {@link AccountSuccessPage} object representing the page displayed after successful registration.
     */
    public AccountSuccessPage clickOnContinueButton() {
        log.info("Executing method: clickOnContinueButton");
        ElementUtilities.clickOnElement(driver, continueButton);
        AccountSuccessPage accountSuccessPage = new AccountSuccessPage(driver);
        log.info("Method clickOnContinueButton completed. Navigating to AccountSuccessPage.");
        return accountSuccessPage;
    }

    /**
     * Clicks the 'login page' link, typically found on the registration page, to navigate to the Login page.
     * @return A {@link LoginPage} object.
     */
    public LoginPage selectLoginPageOption() {
        log.info("Executing method: selectLoginPageOption");
        ElementUtilities.clickOnElement(driver, loginPageOption);
        LoginPage loginPage = new LoginPage(driver);
        log.info("Method selectLoginPageOption completed. Navigating to LoginPage.");
        return loginPage;
    }

    /**
     * Fills out the registration form with the provided details and submits it.
     * This is a convenience method combining multiple actions.
     *
     * @param firstNameText The first name.
     * @param lastNameText The last name.
     * @param emailText The email address.
     * @param telephoneText The telephone number.
     * @param passwordText The password.
     * @return An {@link AccountSuccessPage} object representing the page displayed after successful registration.
     */
    public AccountSuccessPage registeringAnAccount(String firstNameText, String lastNameText, String emailText, String telephoneText, String passwordText) {
        log.info("Executing method: registeringAnAccount with parameters: firstNameText={}, lastNameText={}, emailText={}, telephoneText={}, passwordText=****",
                firstNameText, lastNameText, emailText, telephoneText);
        enterFirstName(firstNameText);
        enterLastName(lastNameText);
        enterEmail(emailText);
        enterTelephone(telephoneText);
        enterPassword(passwordText);
        enterConfirmationPassword(passwordText);
        selectPrivacyPolicyField();
        AccountSuccessPage accountSuccessPage = clickOnContinueButton();
        log.info("Method registeringAnAccount completed. Navigating to AccountSuccessPage.");
        return accountSuccessPage;
    }

    // --- Methods for retrieving element attributes, CSS values, or states ---

    /**
     * Gets the value of a DOM attribute for the password field.
     * @param attributeName The name of the DOM attribute.
     * @return The attribute's value.
     */
    public String getPasswordFieldDomAttribute(String attributeName) {
        log.info("Executing method: getPasswordFieldDomAttribute with parameters: attributeName={}", attributeName);
        String value = ElementUtilities.getElementDomAttribute(driver, passwordField, attributeName);
        log.info("Method getPasswordFieldDomAttribute completed. Returning: {}", value);
        return value;
    }

    /**
     * Gets the value of a DOM attribute for the password confirmation field.
     * @param attributeName The name of the DOM attribute.
     * @return The attribute's value.
     */
    public String getPswdConfirmFieldDomAttribute(String attributeName) {
        log.info("Executing method: getPswdConfirmFieldDomAttribute with parameters: attributeName={}", attributeName);
        String value = ElementUtilities.getElementDomAttribute(driver, passwordConfirmField, attributeName);
        log.info("Method getPswdConfirmFieldDomAttribute completed. Returning: {}", value);
        return value;
    }

    /**
     * Checks if the Privacy Policy checkbox is selected.
     * @return {@code true} if selected, {@code false} otherwise.
     */
    public boolean isPrivacyPolicyFieldSelected() {
        log.info("Executing method: isPrivacyPolicyFieldSelected");
        boolean isSelected = ElementUtilities.isElementSelected(driver, privacyPolicyField);
        log.info("Method isPrivacyPolicyFieldSelected completed. Returning: {}", isSelected);
        return isSelected;
    }

    /**
     * Gets a CSS property value for the first name field.
     * @param propertyName The name of the CSS property.
     * @return The CSS property's value.
     */
    public String getFirstNameCSSValue(String propertyName) {
        log.info("Executing method: getFirstNameCSSValue with parameters: propertyName={}", propertyName);
        String value = ElementUtilities.getElementCSSValue(driver, firstNameField, propertyName);
        log.info("Method getFirstNameCSSValue completed. Returning: {}", value);
        return value;
    }

    /**
     * Gets a CSS property value for the last name field.
     * @param propertyName The name of the CSS property.
     * @return The CSS property's value.
     */
    public String getLastNameCSSValue(String propertyName) {
        log.info("Executing method: getLastNameCSSValue with parameters: propertyName={}", propertyName);
        String value = ElementUtilities.getElementCSSValue(driver, lastNameField, propertyName);
        log.info("Method getLastNameCSSValue completed. Returning: {}", value);
        return value;
    }

    /**
     * Gets a CSS property value for the email field.
     * @param propertyName The name of the CSS property.
     * @return The CSS property's value.
     */
    public String getEmailCSSValue(String propertyName) {
        log.info("Executing method: getEmailCSSValue with parameters: propertyName={}", propertyName);
        String value = ElementUtilities.getElementCSSValue(driver, emailField, propertyName);
        log.info("Method getEmailCSSValue completed. Returning: {}", value);
        return value;
    }

    /**
     * Gets a CSS property value for the telephone field.
     * @param propertyName The name of the CSS property.
     * @return The CSS property's value.
     */
    public String getTelephoneCSSValue(String propertyName) {
        log.info("Executing method: getTelephoneCSSValue with parameters: propertyName={}", propertyName);
        String value = ElementUtilities.getElementCSSValue(driver, telephoneField, propertyName);
        log.info("Method getTelephoneCSSValue completed. Returning: {}", value);
        return value;
    }

    /**
     * Gets a CSS property value for the password field.
     * @param propertyName The name of the CSS property.
     * @return The CSS property's value.
     */
    public String getPasswordCSSValue(String propertyName) {
        log.info("Executing method: getPasswordCSSValue with parameters: propertyName={}", propertyName);
        String value = ElementUtilities.getElementCSSValue(driver, passwordField, propertyName);
        log.info("Method getPasswordCSSValue completed. Returning: {}", value);
        return value;
    }

    /**
     * Gets a CSS property value for the password confirmation field.
     * @param propertyName The name of the CSS property.
     * @return The CSS property's value.
     */
    public String getPasswordConfirmCSSValue(String propertyName) {
        log.info("Executing method: getPasswordConfirmCSSValue with parameters: propertyName={}", propertyName);
        String value = ElementUtilities.getElementCSSValue(driver, passwordConfirmField, propertyName);
        log.info("Method getPasswordConfirmCSSValue completed. Returning: {}", value);
        return value;
    }

    /**
     * Gets a CSS property value for the 'Continue' button.
     * @param propertyName The name of the CSS property.
     * @return The CSS property's value.
     */
    public String getContinueButtonCSSValue(String propertyName) {
        log.info("Executing method: getContinueButtonCSSValue with parameters: propertyName={}", propertyName);
        String value = ElementUtilities.getElementCSSValue(driver, continueButton, propertyName);
        log.info("Method getContinueButtonCSSValue completed. Returning: {}", value);
        return value;
    }

    // --- Methods for clearing input fields ---

    /** Clears the password field. */
    public void clearPasswordField() {
        log.info("Executing method: clearPasswordField");
        ElementUtilities.clearTextFromElement(driver, passwordField);
        log.info("Method clearPasswordField completed.");
    }

    /** Clears the telephone field. */
    public void clearTelephoneField() {
        log.info("Executing method: clearTelephoneField");
        ElementUtilities.clearTextFromElement(driver, telephoneField);
        log.info("Method clearTelephoneField completed.");
    }

    /** Clears the first name field. */
    public void clearFirstNameField() {
        log.info("Executing method: clearFirstNameField");
        ElementUtilities.clearTextFromElement(driver, firstNameField);
        log.info("Method clearFirstNameField completed.");
    }

    /** Clears the last name field. */
    public void clearLastNameField() {
        log.info("Executing method: clearLastNameField");
        ElementUtilities.clearTextFromElement(driver, lastNameField);
        log.info("Method clearLastNameField completed.");
    }

    /** Clears the email field. */
    public void clearEmailField() {
        log.info("Executing method: clearEmailField");
        ElementUtilities.clearTextFromElement(driver, emailField);
        log.info("Method clearEmailField completed.");
    }

    // --- Methods for checking visibility of warning messages ---

    /** @return {@code true} if the first name warning message is displayed, {@code false} otherwise. */
    public boolean isFirstNameWarningMessageDisplayed() {
        log.info("Executing method: isFirstNameWarningMessageDisplayed");
        boolean isDisplayed = ElementUtilities.isElementDisplayed(driver, firstNameWarning);
        log.info("Method isFirstNameWarningMessageDisplayed completed. Returning: {}", isDisplayed);
        return isDisplayed;
    }

    /** @return {@code true} if the last name warning message is displayed, {@code false} otherwise. */
    public boolean isLastNameWarningMessageDisplayed() {
        log.info("Executing method: isLastNameWarningMessageDisplayed");
        boolean isDisplayed = ElementUtilities.isElementDisplayed(driver, lastNameWarning);
        log.info("Method isLastNameWarningMessageDisplayed completed. Returning: {}", isDisplayed);
        return isDisplayed;
    }

    /** @return {@code true} if the email warning message is displayed, {@code false} otherwise. */
    public boolean isEmailWarningMessageDisplayed() {
        log.info("Executing method: isEmailWarningMessageDisplayed");
        boolean isDisplayed = ElementUtilities.isElementDisplayed(driver, emailWarning);
        log.info("Method isEmailWarningMessageDisplayed completed. Returning: {}", isDisplayed);
        return isDisplayed;
    }

    /** @return {@code true} if the telephone warning message is displayed, {@code false} otherwise. */
    public boolean isTelephoneWarningMessageDisplayed() {
        log.info("Executing method: isTelephoneWarningMessageDisplayed");
        boolean isDisplayed = ElementUtilities.isElementDisplayed(driver, telephoneWarning);
        log.info("Method isTelephoneWarningMessageDisplayed completed. Returning: {}", isDisplayed);
        return isDisplayed;
    }

    /** @return {@code true} if the password warning message is displayed, {@code false} otherwise. */
    public boolean isPasswordWarningMessageDisplayed() {
        log.info("Executing method: isPasswordWarningMessageDisplayed");
        boolean isDisplayed = ElementUtilities.isElementDisplayed(driver, passwordWarning);
        log.info("Method isPasswordWarningMessageDisplayed completed. Returning: {}", isDisplayed);
        return isDisplayed;
    }

    // --- Methods for retrieving label WebElements (useful for assertions or advanced interactions) ---

    /** @return The WebElement for the Privacy Policy field label. */
    public WebElement getPrivacyPolicyFieldLabelElement() {
        log.info("Executing method: getPrivacyPolicyFieldLabelElement");
        log.info("Method getPrivacyPolicyFieldLabelElement completed. Returning WebElement.");
        return privacyPolicyFieldLabel;
    }

    /** @return The WebElement for the password confirmation field label. */
    public WebElement getPasswordConfirmFieldLabelElement() {
        log.info("Executing method: getPasswordConfirmFieldLabelElement");
        log.info("Method getPasswordConfirmFieldLabelElement completed. Returning WebElement.");
        return passwordConfirmFieldLabel;
    }

    /** @return The WebElement for the password field label. */
    public WebElement getPasswordFieldLabelElement() {
        log.info("Executing method: getPasswordFieldLabelElement");
        log.info("Method getPasswordFieldLabelElement completed. Returning WebElement.");
        return passwordFieldLabel;
    }

    /** @return The WebElement for the telephone field label. */
    public WebElement getTelephoneFieldLabelElement() {
        log.info("Executing method: getTelephoneFieldLabelElement");
        log.info("Method getTelephoneFieldLabelElement completed. Returning WebElement.");
        return telephoneFieldLabel;
    }

    /** @return The WebElement for the email field label. */
    public WebElement getEmailFieldLabelElement() {
        log.info("Executing method: getEmailFieldLabelElement");
        log.info("Method getEmailFieldLabelElement completed. Returning WebElement.");
        return emailFieldLabel;
    }

    /** @return The WebElement for the first name field label. */
    public WebElement getFirstNameFieldLabelElement() {
        log.info("Executing method: getFirstNameFieldLabelElement");
        log.info("Method getFirstNameFieldLabelElement completed. Returning WebElement.");
        return firstNameFieldLabel;
    }

    /** @return The WebElement for the last name field label. */
    public WebElement getLastNameFieldLabelElement() {
        log.info("Executing method: getLastNameFieldLabelElement");
        log.info("Method getLastNameFieldLabelElement completed. Returning WebElement.");
        return lastNameFieldLabel;
    }

    // --- Methods for retrieving placeholder text ---

    /** @return The placeholder text for the first name field. */
    public String getFirstNameFieldPlaceholderText() {
        log.info("Executing method: getFirstNameFieldPlaceholderText");
        String placeholder = ElementUtilities.getElementDomAttribute(driver, firstNameField, "placeholder");
        log.info("Method getFirstNameFieldPlaceholderText completed. Returning: {}", placeholder);
        return placeholder;
    }

    /** @return The placeholder text for the last name field. */
    public String getLastNameFieldPlaceholderText() {
        log.info("Executing method: getLastNameFieldPlaceholderText");
        String placeholder = ElementUtilities.getElementDomAttribute(driver, lastNameField, "placeholder");
        log.info("Method getLastNameFieldPlaceholderText completed. Returning: {}", placeholder);
        return placeholder;
    }

    /** @return The placeholder text for the email field. */
    public String getEmailFieldPlaceholderText() {
        log.info("Executing method: getEmailFieldPlaceholderText");
        String placeholder = ElementUtilities.getElementDomAttribute(driver, emailField, "placeholder");
        log.info("Method getEmailFieldPlaceholderText completed. Returning: {}", placeholder);
        return placeholder;
    }

    /** @return The placeholder text for the telephone field. */
    public String getTelephoneFieldPlaceholderText() {
        log.info("Executing method: getTelephoneFieldPlaceholderText");
        String placeholder = ElementUtilities.getElementDomAttribute(driver, telephoneField, "placeholder");
        log.info("Method getTelephoneFieldPlaceholderText completed. Returning: {}", placeholder);
        return placeholder;
    }

    /** @return The placeholder text for the password field. */
    public String getPasswordFieldPlaceholderText() {
        log.info("Executing method: getPasswordFieldPlaceholderText");
        String placeholder = ElementUtilities.getElementDomAttribute(driver, passwordField, "placeholder");
        log.info("Method getPasswordFieldPlaceholderText completed. Returning: {}", placeholder);
        return placeholder;
    }

    /** @return The placeholder text for the password confirmation field. */
    public String getPasswordConfirmFieldPlaceholderText() {
        log.info("Executing method: getPasswordConfirmFieldPlaceholderText");
        String placeholder = ElementUtilities.getElementDomAttribute(driver, passwordConfirmField, "placeholder");
        log.info("Method getPasswordConfirmFieldPlaceholderText completed. Returning: {}", placeholder);
        return placeholder;
    }

    // --- Methods for retrieving validation messages or warning text ---

    /** @return The HTML5 validation message for the email field. */
    public String getEmailValidationMessage() {
        log.info("Executing method: getEmailValidationMessage");
        String message = ElementUtilities.getElementDomProperty(driver, emailField, "validationMessage");
        log.info("Method getEmailValidationMessage completed. Returning: {}", message);
        return message;
    }

    /**
     * Clicks the 'Register' breadcrumb link.
     * @return A new {@link RegisterPage} instance (representing the same page, useful for method chaining if needed).
     */
    public RegisterPage selectRegisterBreadcrumbOption() {
        log.info("Executing method: selectRegisterBreadcrumbOption");
        ElementUtilities.clickOnElement(driver, registerPageBreadcrumb);
        RegisterPage newRegisterPage = new RegisterPage(driver);
        log.info("Method selectRegisterBreadcrumbOption completed. Navigating to RegisterPage.");
        return newRegisterPage;
    }

    /**
     * Checks if the 'Register' breadcrumb is displayed, indicating the user is on the registration page.
     * @return {@code true} if the 'Register' breadcrumb is displayed, {@code false} otherwise.
     */
    public boolean didWeNavigateToRegisterPage() {
        log.info("Executing method: didWeNavigateToRegisterPage");
        boolean isDisplayed = ElementUtilities.isElementDisplayed(driver, registerPageBreadcrumb);
        log.info("Method didWeNavigateToRegisterPage completed. Returning: {}", isDisplayed);
        return isDisplayed;
    }

    /** @return The text of the password confirmation warning message. */
    public String getPasswordConfirmationWarning() {
        log.info("Executing method: getPasswordConfirmationWarning");
        String text = ElementUtilities.getElementText(driver, passwordConfirmationWarning);
        log.info("Method getPasswordConfirmationWarning completed. Returning: {}", text);
        return text;
    }

    /** @return The text of the password warning message. */
    public String getPasswordWarning() {
        log.info("Executing method: getPasswordWarning");
        String text = ElementUtilities.getElementText(driver, passwordWarning);
        log.info("Method getPasswordWarning completed. Returning: {}", text);
        return text;
    }

    /** @return The text of the email warning message. */
    public String getEmailWarning() {
        log.info("Executing method: getEmailWarning");
        String text = ElementUtilities.getElementText(driver, emailWarning);
        log.info("Method getEmailWarning completed. Returning: {}", text);
        return text;
    }

    /** @return The text of the telephone warning message. */
    public String getTelephoneWarning() {
        log.info("Executing method: getTelephoneWarning");
        String text = ElementUtilities.getElementText(driver, telephoneWarning);
        log.info("Method getTelephoneWarning completed. Returning: {}", text);
        return text;
    }

    /** @return The text of the last name warning message. */
    public String getLastNameWarning() {
        log.info("Executing method: getLastNameWarning");
        String text = ElementUtilities.getElementText(driver, lastNameWarning);
        log.info("Method getLastNameWarning completed. Returning: {}", text);
        return text;
    }

    /** @return The text of the first name warning message. */
    public String getFirstNameWarning() {
        log.info("Executing method: getFirstNameWarning");
        String text = ElementUtilities.getElementText(driver, firstNameWarning);
        log.info("Method getFirstNameWarning completed. Returning: {}", text);
        return text;
    }
}
