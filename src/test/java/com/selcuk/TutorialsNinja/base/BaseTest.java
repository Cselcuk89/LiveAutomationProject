package com.selcuk.TutorialsNinja.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import java.time.Duration;
import com.selcuk.TutorialsNinja.utils.PropertyUtils;

/**
 * BaseTest class serves as the foundation for all test classes in the framework.
 * It handles the common setup and teardown operations for WebDriver instances,
 * ensuring that each test runs with a fresh browser session and is properly closed afterwards.
 * It supports parameterized browser selection from TestNG XML files.
 */
public class BaseTest {
    public WebDriver driver; // Changed to public for access from TestListener
    private static final Logger log = LogManager.getLogger(BaseTest.class);

    /**
     * Default implicit wait time in seconds.
     */
    public static final int IMPLICIT_WAIT_TIME = 10;
    /**
     * Default page load timeout in seconds.
     */
    public static final int PAGE_LOAD_TIMEOUT = 30;

    /**
     * Sets up the WebDriver instance before each test method.
     * Initializes the browser based on the 'browser' parameter passed from the TestNG XML file.
     * Supported browsers are Chrome and Firefox. Defaults to Chrome if no parameter is specified or if the
     * specified browser is not recognized.
     * Configures implicit wait, page load timeout, and maximizes the browser window.
     *
     * @param browser The name of the browser to initialize (e.g., "chrome", "firefox").
     *                This parameter is optional and defaults to "chrome" if not provided in testng.xml.
     * @throws Exception if an error occurs during WebDriver setup.
     */
    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        log.info("Setting up WebDriver for browser: {}", browser);
        try {
            if (browser.equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                log.info("FirefoxDriver initialized.");
            } else if (browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                log.info("ChromeDriver initialized.");
            } else {
                log.warn("Browser '{}' not recognized, defaulting to Chrome.", browser);
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                log.info("Defaulted to ChromeDriver.");
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_TIME));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
            driver.manage().window().maximize();
            log.info("WebDriver setup complete. Implicit wait: {}s, Page load timeout: {}s.", IMPLICIT_WAIT_TIME, PAGE_LOAD_TIMEOUT);

        } catch (Exception e) {
            log.error("Error during WebDriver setup: ", e);
            throw e; // Fail fast
        }
    }

    /**
     * Tears down the WebDriver instance after each test method.
     * Quits the browser and handles any potential exceptions during the process.
     */
    @AfterMethod
    public void tearDown() {
        log.info("Tearing down WebDriver.");
        if (driver != null) {
            try {
                driver.quit();
                log.info("WebDriver quit successfully.");
            } catch (Exception e) {
                log.error("Error during WebDriver quit: ", e);
            }
        }
    }
}
