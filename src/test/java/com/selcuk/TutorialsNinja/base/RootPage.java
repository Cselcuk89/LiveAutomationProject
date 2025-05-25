package com.selcuk.TutorialsNinja.base;

import com.selcuk.TutorialsNinja.utils.ElementUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

/**
 * RootPage class serves as a base class for all Page Objects in the TutorialsNinja application.
 * It initializes common WebElements found across multiple pages or provides utility methods
 * that are applicable to any page. It holds a WebDriver instance passed to it by subclasses,
 * which in turn receive it from test classes.
 * <p>
 * This class uses {@link PageFactory} to initialize WebElements.
 * </p>
 */
public class RootPage {
    private static final Logger log = LogManager.getLogger(RootPage.class);
    protected WebDriver driver;

    /**
     * Constructor for RootPage. Initializes WebElements using PageFactory.
     *
     * @param driver The WebDriver instance to be used by this page object.
     */
    public RootPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        log.info("RootPage initialized with WebDriver for class: {}", this.getClass().getSimpleName());
    }

    // --- WebElements common to many pages ---

    /** WebElement representing the main heading of a page, typically an H1 tag within a content div. */
    @FindBy(how = How.XPATH, using = "//div[@id='content']/h1")
    private WebElement pageHeading;

    /** WebElement for the 'Home' icon in breadcrumbs. */
    @FindBy(how = How.XPATH, using = "//i[@class='fa fa-home']")
    private WebElement homeBreadCrumb;

    /** WebElement for the 'Account' link in breadcrumbs. */
    @FindBy(how = How.XPATH, using = "//ul[@class='breadcrumb']//a[text()='Account']")
    private WebElement accountBreadCrumb;

    /** WebElement for page-level warning messages (e.g., error alerts). */
    @FindBy(how = How.XPATH, using = "//div[@class='alert alert-danger alert-dismissible']")
    private WebElement pageLevelWarning;

    /** WebElement for page-level success messages. */
    @FindBy(how = How.XPATH, using = "//div[@class='alert alert-success alert-dismissible']")
    private WebElement pageLevelSuccessMessage;

    // --- Common Page Actions ---

    /**
     * Gets the text of the main page heading.
     * @return The text content of the page heading element.
     */
    public String getPageHeadingText() {
        log.info("Executing method: getPageHeadingText");
        String headingText = ElementUtilities.getElementText(driver, pageHeading);
        log.info("Method getPageHeadingText completed. Returning: {}", headingText);
        return headingText;
    }

    /**
     * Clicks on the 'Home' breadcrumb link.
     * Typically navigates to the homepage.
     */
    public void clickHomeBreadcrumb() {
        log.info("Executing method: clickHomeBreadcrumb");
        ElementUtilities.clickOnElement(driver, homeBreadCrumb);
        log.info("Method clickHomeBreadcrumb completed.");
    }

    /**
     * Clicks on the 'Account' breadcrumb link.
     * Typically navigates to an account-related page.
     */
    public void clickAccountBreadcrumb() {
        log.info("Executing method: clickAccountBreadcrumb");
        ElementUtilities.clickOnElement(driver, accountBreadCrumb);
        log.info("Method clickAccountBreadcrumb completed.");
    }

    /**
     * Gets the text of any page-level warning message.
     * @return The text of the warning message, or an empty string if not displayed.
     */
    public String getPageLevelWarningText() {
        log.info("Executing method: getPageLevelWarningText");
        String warningText = ElementUtilities.getElementText(driver, pageLevelWarning);
        log.info("Method getPageLevelWarningText completed. Returning: {}", warningText);
        return warningText;
    }

    /**
     * Checks if a page-level warning message is currently displayed.
     * @return {@code true} if a warning message is displayed, {@code false} otherwise.
     */
    public boolean isPageLevelWarningDisplayed() {
        log.info("Executing method: isPageLevelWarningDisplayed");
        boolean isDisplayed = ElementUtilities.isElementDisplayed(driver, pageLevelWarning);
        log.info("Method isPageLevelWarningDisplayed completed. Returning: {}", isDisplayed);
        return isDisplayed;
    }

    /**
     * Gets the text of any page-level success message.
     * @return The text of the success message, or an empty string if not displayed.
     */
    public String getPageLevelSuccessMessageText() {
        log.info("Executing method: getPageLevelSuccessMessageText");
        String successMessage = ElementUtilities.getElementText(driver, pageLevelSuccessMessage);
        log.info("Method getPageLevelSuccessMessageText completed. Returning: {}", successMessage);
        return successMessage;
    }

    /**
     * Checks if a page-level success message is currently displayed.
     * @return {@code true} if a success message is displayed, {@code false} otherwise.
     */
    public boolean isPageLevelSuccessMessageDisplayed() {
        log.info("Executing method: isPageLevelSuccessMessageDisplayed");
        boolean isDisplayed = ElementUtilities.isElementDisplayed(driver, pageLevelSuccessMessage);
        log.info("Method isPageLevelSuccessMessageDisplayed completed. Returning: {}", isDisplayed);
        return isDisplayed;
    }

    /**
     * Gets the title of the current page from the browser.
     * @return The current page title as a String.
     */
    public String getCurrentPageTitle() {
        log.info("Executing method: getCurrentPageTitle");
        String title = driver.getTitle();
        log.info("Method getCurrentPageTitle completed. Returning title: {}", title);
        return title;
    }

    /**
     * Gets the current URL of the page from the browser.
     * @return The current page URL as a String.
     */
    public String getCurrentPageUrl() {
        log.info("Executing method: getCurrentPageUrl");
        String url = driver.getCurrentUrl();
        log.info("Method getCurrentPageUrl completed. Returning URL: {}", url);
        return url;
    }
}
