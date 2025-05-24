package com.selcuk.FrameworkPages.pageObjects;

import com.selcuk.FrameworkPages.root.RootPage;
import com.selcuk.ProjectUtils.ElementUtilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage extends RootPage {
    WebDriver driver;
    public RegisterPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
    @FindBy(how = How.ID,using = "input-firstname")
    private WebElement firstNameField;

    @FindBy(how = How.ID,using = "input-lastname")
    private WebElement lastNameField;

    @FindBy(how = How.ID,using = "input-email")
    private WebElement emailField;

    @FindBy(how = How.ID,using = "input-telephone")
    private WebElement telephoneField;

    @FindBy(how = How.ID,using = "input-password")
    private WebElement passwordField;

    @FindBy(how = How.ID,using = "input-confirm")
    private WebElement passwordConfirmField;

    @FindBy(how = How.NAME,using = "agree")
    private WebElement privacyPolicyField;

    @FindBy(how = How.XPATH,using = "//input[@name='newsletter'][@value='1']")
    private WebElement yesNewsletterOption;

    @FindBy(how = How.XPATH,using = "//input[@name='newsletter'][@value='0']")
    private WebElement noNewsletterOption;

    @FindBy(how = How.XPATH,using = "//input[@value='Continue']")
    private WebElement continueButton;

    @FindBy(how = How.XPATH,using = "//input[@id='input-firstname']/following-sibling::div")
    private WebElement firstNameWarning;

    @FindBy(how = How.XPATH,using = "//input[@id='input-lastname']/following-sibling::div")
    private WebElement lastNameWarning;

    @FindBy(how = How.XPATH,using = "//input[@id='input-email']/following-sibling::div")
    private WebElement emailWarning;

    @FindBy(how = How.XPATH,using = "//input[@id='input-telephone']/following-sibling::div")
    private WebElement telephoneWarning;

    @FindBy(how = How.XPATH,using = "//input[@id='input-password']/following-sibling::div")
    private WebElement passwordWarning;

    @FindBy(how = How.XPATH,using = "//input[@id='input-confirm']/following-sibling::div")
    private WebElement passwordConfirmationWarning;

    @FindBy(how = How.XPATH,using = "//ul[@class='breadcrumb']//a[text()='Register']")
    private WebElement registerPageBreadcrumb;

    @FindBy(how = How.CSS,using = "label[for='input-firstname']")
    private WebElement firstNameFieldLabel;

    @FindBy(how = How.CSS,using ="label[for='input-lastname']")
    private WebElement lastNameFieldLabel;

    @FindBy(how = How.CSS,using ="label[for='input-email']")
    private WebElement emailFieldLabel;

    @FindBy(how = How.CSS,using ="label[for='input-telephone']")
    private WebElement telephoneFieldLabel;

    @FindBy(how = How.CSS,using ="label[for='input-password']")
    private WebElement passwordFieldLabel;

    @FindBy(how = How.CSS,using ="label[for='input-confirm']")
    private WebElement passwordConfirmFieldLabel;

    @FindBy(how = How.CSS,using ="div[class='pull-right']")
    private WebElement privacyPolicyFieldLabel;

    @FindBy(how = How.LINK_TEXT,using = "login page")
    private WebElement loginPageOption;
    public LoginPage selectLoginPageOption(){
        ElementUtilities.clickOnElement(loginPageOption);
        return new LoginPage();
    }
    public String getPasswordFieldDomAttribute(String attributeName){
        return ElementUtilities.getElementDomAttribute(passwordField,attributeName);
    }
    public String getPswdConfirmFieldDomAttribute(String attributeName){
        return ElementUtilities.getElementDomAttribute(passwordConfirmField,attributeName);
    }
    public boolean isPrivacyPolicyFieldSelected(){
        return ElementUtilities.isElementSelected(privacyPolicyField);
    }
    public String getFirstNameCSSValue(String propertyName){
        return ElementUtilities.getElementCSSValue(firstNameField,propertyName);
    }
    public String getLastNameCSSValue(String propertyName){
        return ElementUtilities.getElementCSSValue(lastNameField,propertyName);
    }
    public String getEmailCSSValue(String propertyName){
        return ElementUtilities.getElementCSSValue(emailField,propertyName);
    }
    public String getTelephoneCSSValue(String propertyName){
        return ElementUtilities.getElementCSSValue(telephoneField,propertyName);
    }
    public String getPasswordCSSValue(String propertyName){
        return ElementUtilities.getElementCSSValue(passwordField,propertyName);
    }
    public String getPasswordConfirmCSSValue(String propertyName){
        return ElementUtilities.getElementCSSValue(passwordConfirmField,propertyName);
    }
    public String getContinueButtonCSSValue(String propertyName) {
        return ElementUtilities.getElementCSSValue(continueButton, propertyName);
    }
    public void clearPasswordField(){
        ElementUtilities.clearTextFromElement(passwordField);
    }
    public void clearTelephoneField(){
        ElementUtilities.clearTextFromElement(telephoneField);
    }
    public void clearFirstNameField(){
        ElementUtilities.clearTextFromElement(firstNameField);
    }
    public void clearLastNameField(){
        ElementUtilities.clearTextFromElement(lastNameField);
    }
    public boolean isFirstNameWarningMessageDisplayed() {
        return ElementUtilities.isElementDisplayed(firstNameWarning);
    }

    public boolean isLastNameWarningMessageDisplayed() {
        return ElementUtilities.isElementDisplayed(lastNameWarning);
    }

    public boolean isEmailWarningMessageDisplayed() {
        return ElementUtilities.isElementDisplayed(emailWarning);
    }

    public boolean isTelephoneWarningMessageDisplayed() {
        return ElementUtilities.isElementDisplayed(telephoneWarning);
    }

    public boolean isPasswordWarningMessageDisplayed() {
        return ElementUtilities.isElementDisplayed(passwordWarning);
    }
    public WebElement getPrivacyPolicyFieldLabelElement() {
        return privacyPolicyFieldLabel;
    }

    public WebElement getPasswordConfirmFieldLabelElement() {
        return passwordConfirmFieldLabel;
    }

    public WebElement getPasswordFieldLabelElement() {
        return passwordFieldLabel;
    }

    public WebElement getTelephoneFieldLabelElement() {
        return telephoneFieldLabel;
    }

    public WebElement getEmailFieldLabelElement() {
        return emailFieldLabel;
    }

    public WebElement getFirstNameFieldLabelElement() {
        return firstNameFieldLabel;
    }

    public WebElement getLastNameFieldLabelElement() {
        return lastNameFieldLabel;
    }
    public String getFirstNameFieldPlaceholderText() {
        return ElementUtilities.getElementDomAttribute(firstNameField,"placeholder");
    }

    public String getLastNameFieldPlaceholderText() {
        return ElementUtilities.getElementDomAttribute(lastNameField, "placeholder");
    }

    public String getEmailFieldPlaceholderText() {
        return ElementUtilities.getElementDomAttribute(emailField,"placeholder");
    }

    public String getTelephoneFieldPlaceholderText() {
        return ElementUtilities.getElementDomAttribute(telephoneField,"placeholder");
    }

    public String getPasswordFieldPlaceholderText() {
        return ElementUtilities.getElementDomAttribute(passwordField,"placeholder");
    }

    public String getPasswordConfirmFieldPlaceholderText() {
        return ElementUtilities.getElementDomAttribute(passwordConfirmField,"placeholder");
    }
    public void clearEmailField() {
        ElementUtilities.clearTextFromElement(emailField);
    }

    public String getEmailValidationMessage() {
        return ElementUtilities.getElementDomProperty(emailField,"validationMessage");
    }

    public RegisterPage selectRegisterBreadcrumbOption() {
        ElementUtilities.clickOnElement(registerPageBreadcrumb);
        return new RegisterPage(driver);
    }

    public boolean didWeNavigateToRegisterPage() {
        return ElementUtilities.isElementDisplayed(registerPageBreadcrumb);
    }

    public String getPasswordConfirmationWarning() {
        return ElementUtilities.getElementText(passwordConfirmationWarning);
    }



    public String getPasswordWarning() {
        return ElementUtilities.getElementText(passwordWarning);
    }

    public String getEmailWarning() {
        return ElementUtilities.getElementText(emailWarning);
    }

    public String getTelephoneWarning() {
        return ElementUtilities.getElementText(telephoneWarning);
    }

    public String getLastNameWarning() {
        return ElementUtilities.getElementText(lastNameWarning);
    }

    public String getFirstNameWarning() {
        return ElementUtilities.getElementText(firstNameWarning);
    }

    public void selectYesNewsletterOption() {
        ElementUtilities.clickOnElement(yesNewsletterOption);
    }

    public void selectNoNewletterOption() {
        ElementUtilities.clickOnElement(noNewsletterOption);
    }

    public AccountSuccessPage clickOnContinueButton() {
        ElementUtilities.clickOnElement(continueButton);
        return new AccountSuccessPage();
    }

    public void selectPrivacyPolicyField() {
        ElementUtilities.clickOnElement(privacyPolicyField);
    }

    public void enterConfirmationPassword(String passwordText) {
        ElementUtilities.enterTextIntoElement(passwordConfirmField, passwordText);
    }

    public AccountSuccessPage registeringAnAccount(String firstNameText,String lastNameText,String emailText,String telephoneText,String passwordText) {
        enterFirstName(firstNameText);
        enterLastName(lastNameText);
        enterEmail(emailText);
        enterTelephone(telephoneText);
        enterPassword(passwordText);
        enterConfirmationPassword(passwordText);
        selectPrivacyPolicyField();
        return clickOnContinueButton();
    }

    public void enterFirstName(String firstNameText) {
        ElementUtilities.enterTextIntoElement(firstNameField, firstNameText);
    }

    public void enterLastName(String lastNameText) {
        ElementUtilities.enterTextIntoElement(lastNameField, lastNameText);
    }

    public void enterEmail(String emailText) {
        ElementUtilities.enterTextIntoElement(emailField, emailText);
    }

    public void enterTelephone(String telephoneText) {
        ElementUtilities.enterTextIntoElement(telephoneField, telephoneText);
    }

    public void enterPassword(String passwordText) {
        ElementUtilities.enterTextIntoElement(passwordField, passwordText);
    }





}
