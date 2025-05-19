package com.selcuk.FrameworkPages.pageObjects;

import com.selcuk.FrameworkPages.root.RootPage;
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
}
