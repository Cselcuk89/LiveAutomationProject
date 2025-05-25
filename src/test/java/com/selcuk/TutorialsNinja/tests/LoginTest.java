package com.selcuk.TutorialsNinja.tests;

import com.selcuk.TutorialsNinja.base.BaseTest;
import com.selcuk.TutorialsNinja.pages.LoginPage;
import com.selcuk.TutorialsNinja.pages.AccountSuccessPage;
import com.selcuk.TutorialsNinja.utils.PropertyUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.selcuk.TutorialsNinja.utils.MyXLSReader;

/**
 * LoginTest class contains test methods related to the login functionality
 * of the TutorialsNinja application. It extends {@link BaseTest} to inherit
 * common test setup and teardown procedures.
 */
public class LoginTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(LoginTest.class);
    private MyXLSReader xlsReader;

    /**
     * Constructor for LoginTest. Initializes {@link MyXLSReader} to read test data
     * from the "TutorialsNinjaTestData.xlsx" Excel file.
     * Logs an error if the Excel reader cannot be initialized.
     */
    public LoginTest() {
        try {
            // It's generally better to initialize resources like this in a @BeforeClass or @BeforeSuite method
            // if they are expensive to create or if the path needs to be more dynamic.
            // For this context, constructor initialization is as per existing structure.
            xlsReader = new MyXLSReader("src/test/resources/TutorialsNinjaTestData.xlsx");
            log.info("MyXLSReader initialized successfully for LoginTest.");
        } catch (Exception e) {
            log.error("Failed to initialize MyXLSReader for LoginTest. Data-driven tests may fail.", e);
            // Consider re-throwing or setting a flag if XLSReader is critical for all tests in this class.
        }
    }

    /**
     * Tests the successful login functionality using valid credentials obtained from property files.
     * It navigates to the login page, enters credentials, clicks login, and verifies
     * that the logout link is displayed on the subsequent page (AccountSuccessPage),
     * indicating a successful login.
     */
    @Test(priority = 1, description = "Verify successful login with valid credentials.")
    public void testSuccessfulLogin() {
        log.info("Starting testSuccessfulLogin...");
        String loginPageUrl = PropertyUtils.getProperty("appURL", "http://tutorialsninja.com/demo/") + "index.php?route=account/login";
        driver.get(loginPageUrl);
        log.info("Navigated to application login page URL: {}", loginPageUrl);

        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.enterEmail(PropertyUtils.getProperty("existingEmail")); // Using existing key
        loginPage.enterPassword(PropertyUtils.getProperty("validPassword")); // Using existing key
        AccountSuccessPage accountPage = loginPage.clickLoginButton(); 

        Assert.assertTrue(accountPage.isLogoutLinkDisplayed(), "Login was not successful, logout link not found on the Account Success Page.");
        log.info("Login successful, logout link displayed on Account Success Page.");
    }

    /**
     * DataProvider method to supply invalid login credentials from the "LoginData" sheet
     * in the "TutorialsNinjaTestData.xlsx" Excel file.
     *
     * @return A 2D Object array where each inner array contains:
     *         {String email, String password, String expectedErrorMessage}.
     *         Returns an empty array if the XLSReader is not initialized or the sheet is empty/not found.
     */
    @DataProvider(name = "invalidLoginData")
    public Object[][] getInvalidLoginData() {
        String sheetName = "LoginData";
        log.info("DataProvider 'invalidLoginData' attempting to read from sheet: {}", sheetName);
        if (xlsReader == null || xlsReader.workbook == null) {
            log.error("XLSReader not initialized or workbook is null for DataProvider 'invalidLoginData'. Returning empty data set.");
            return new Object[0][0];
        }
        // Assuming header is row 1, data starts from row 2.
        int rowCount = xlsReader.getRowCount(sheetName);
        int dataRowCount = rowCount -1; // Number of actual data rows

        if (dataRowCount <= 0) {
             log.warn("No data rows found in sheet '{}' for invalidLoginData (rowCount: {}). Returning empty data set.", sheetName, rowCount);
            return new Object[0][0];
        }

        // Assuming 3 columns: Email, Password, ExpectedErrorMessage
        int colCount = xlsReader.getColumnCount(sheetName) > 0 ? xlsReader.getColumnCount(sheetName) : 3;
        if (xlsReader.getColumnCount(sheetName) == 0) {
             log.warn("No columns found in sheet '{}' for invalidLoginData. Assuming 3 columns.", sheetName);
        }

        Object[][] data = new Object[dataRowCount][colCount];
        log.debug("Preparing to read {} data rows and {} columns from sheet '{}'", dataRowCount, colCount, sheetName);

        for (int i = 0; i < dataRowCount; i++) { // Iterate 0 to dataRowCount-1 for the 'data' array
            for (int j = 0; j < colCount; j++) {
                // Excel rows are 1-based, data starts from row 2. Excel cols are 1-based.
                data[i][j] = xlsReader.getCellData(sheetName, j + 1, i + 2);
                log.trace("Data from Excel for 'invalidLoginData': Row_Excel={}, Col_Excel={}: {}", i + 2, j + 1, data[i][j]);
            }
        }
        log.info("DataProvider 'invalidLoginData' successfully read {} data sets.", dataRowCount);
        return data;
    }

    /**
     * Tests the unsuccessful login functionality using invalid credentials provided by the
     * "invalidLoginData" DataProvider.
     * It navigates to the login page, enters invalid credentials, clicks login,
     * and verifies that the expected error message is displayed.
     *
     * @param email The invalid email address to test.
     * @param password The invalid password to test.
     * @param expectedErrorMessage The expected error message string or substring.
     */
    @Test(priority = 2, dataProvider = "invalidLoginData", description = "Verify unsuccessful login with invalid credentials.")
    public void testUnsuccessfulLogin(String email, String password, String expectedErrorMessage) {
        log.info("Starting testUnsuccessfulLogin with Email: {}, Password: ****, ExpectedError: {}", email, expectedErrorMessage);
        String loginPageUrl = PropertyUtils.getProperty("appURL", "http://tutorialsninja.com/demo/") + "index.php?route=account/login";
        driver.get(loginPageUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginButtonExpectingFailure(); 

        String actualErrorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage), 
            "Error message mismatch. Expected to contain: '" + expectedErrorMessage + "', Actual: '" + actualErrorMessage + "'");
        log.info("Unsuccessful login test completed for email: {}. Error message validated.", email);
    }
}
