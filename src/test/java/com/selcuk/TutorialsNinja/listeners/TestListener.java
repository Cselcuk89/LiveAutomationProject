package com.selcuk.TutorialsNinja.listeners;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.selcuk.TutorialsNinja.reports.ExtentManager;
import com.selcuk.TutorialsNinja.reports.ExtentTestManager;
import com.selcuk.TutorialsNinja.utils.CommonUtils; // Added for screenshots
import com.selcuk.TutorialsNinja.base.BaseTest; // To get driver
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver; // Added for WebDriver
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;

/**
 * TestListener class implements the TestNG `ITestListener` interface to provide custom logic
 * for various test execution events. It integrates with Log4j2 for logging and ExtentReports
 * for test reporting, including screenshot capture on test failures.
 */
public class TestListener implements ITestListener {
    private static final Logger log = LogManager.getLogger(TestListener.class);

    /**
     * Called when a test suite starts. Initializes the ExtentReports instance.
     * @param context The test context for the suite that is starting.
     */
    @Override
    public void onStart(ITestContext context) {
        log.info("Test Suite started: " + context.getName());
        log.info("Initializing ExtentReports.");
        ExtentManager.getInstance();
    }

    /**
     * Called when a test suite finishes. Flushes the ExtentReports instance to write report to file.
     * @param context The test context for the suite that has finished.
     */
    @Override
    public void onFinish(ITestContext context) {
        log.info("Test Suite finished: " + context.getName());
        log.info("Flushing ExtentReports.");
        ExtentManager.flushInstance();
    }

    /**
     * Called when a test method starts. Logs the test start and creates a new test entry in ExtentReports.
     * @param result The result of the test method that is starting.
     */
    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        log.info("Test STARTED: " + testName);
        ExtentTestManager.startTest(testName, description != null ? description : "");
        if (ExtentTestManager.getTest() != null) {
            ExtentTestManager.getTest().log(Status.INFO, testName + " started");
        }
    }

    /**
     * Called when a test method passes. Logs the success and marks the test as PASSED in ExtentReports.
     * @param result The result of the test method that passed.
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        log.info("Test PASSED: " + testName);
        if (ExtentTestManager.getTest() != null) {
            ExtentTestManager.getTest().log(Status.PASS, MarkupHelper.createLabel(testName + " - Test Case PASSED", ExtentColor.GREEN));
        }
    }

    /**
     * Called when a test method fails. Logs the failure, captures a screenshot,
     * and marks the test as FAILED in ExtentReports, attaching the screenshot and exception details.
     * @param result The result of the test method that failed.
     */
    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        log.error("Test FAILED: " + testName, result.getThrowable());

        if (ExtentTestManager.getTest() != null) {
            ExtentTestManager.getTest().log(Status.FAIL, MarkupHelper.createLabel(testName + " - Test Case FAILED", ExtentColor.RED));
            ExtentTestManager.getTest().log(Status.FAIL, result.getThrowable());

            Object testInstance = result.getInstance();
            WebDriver driver = null;

            if (testInstance instanceof BaseTest) {
                driver = ((BaseTest) testInstance).driver;
                log.debug("WebDriver instance retrieved from BaseTest instance for test: {}", testName);
            } else {
                log.warn("Could not get WebDriver instance from testInstance for screenshot. Test instance is of type: {}", testInstance.getClass().getName());
            }

            if (driver != null) {
                String screenshotPath = CommonUtils.takeScreenshot(driver, testName);
                if (screenshotPath != null && new File(screenshotPath).exists()) {
                    try {
                        ExtentTestManager.getTest().addScreenCaptureFromPath(screenshotPath, "Failure Screenshot: " + testName);
                        log.info("Screenshot attached to Extent Report: {}", screenshotPath);
                    } catch (Exception e) {
                        log.error("Error attaching screenshot to Extent Report: {}", e.getMessage(), e);
                        ExtentTestManager.getTest().log(Status.WARNING, "Failed to attach screenshot: " + e.getMessage());
                    }
                } else {
                    log.warn("Screenshot path is null or file does not exist. Screenshot not attached for: {}", testName);
                    ExtentTestManager.getTest().log(Status.WARNING, "Screenshot could not be captured or found for: " + testName);
                }
            } else {
                log.warn("WebDriver instance was null in TestListener.onTestFailure. Cannot take screenshot for: {}", testName);
                ExtentTestManager.getTest().log(Status.WARNING, "WebDriver instance not available, screenshot skipped for: " + testName);
            }
        }
    }

    /**
     * Called when a test method is skipped. Logs the skip and marks the test as SKIPPED in ExtentReports.
     * @param result The result of the test method that was skipped.
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        log.warn("Test SKIPPED: " + testName);
        if (ExtentTestManager.getTest() != null) {
            ExtentTestManager.getTest().log(Status.SKIP, MarkupHelper.createLabel(testName + " - Test Case SKIPPED", ExtentColor.ORANGE));
            if (result.getThrowable() != null) {
                 ExtentTestManager.getTest().log(Status.SKIP, "Skip Reason: " + result.getThrowable().getMessage());
            }
        }
    }

    /**
     * Called when a test method fails but is within the defined success percentage.
     * Logs this status and marks it as WARNING in ExtentReports.
     * @param result The result of the test method.
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.warn("Test FAILED but within success percentage: " + result.getMethod().getMethodName());
         if (ExtentTestManager.getTest() != null) {
            ExtentTestManager.getTest().log(Status.WARNING, MarkupHelper.createLabel(result.getMethod().getMethodName() + " - FAILED (within success %)", ExtentColor.YELLOW));
            if (result.getThrowable() != null) {
                 ExtentTestManager.getTest().log(Status.WARNING, result.getThrowable());
            }
        }
    }

    /**
     * Called when a test method fails due to a timeout. Logs the timeout failure,
     * then calls the standard `onTestFailure` logic for consistent reporting and screenshot capture.
     * @param result The result of the test method that timed out.
     */
    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        log.error("Test FAILED due to Timeout: " + result.getMethod().getMethodName(), result.getThrowable());
        this.onTestFailure(result);
        if (ExtentTestManager.getTest() != null) {
             ExtentTestManager.getTest().log(Status.FAIL, MarkupHelper.createLabel(result.getMethod().getMethodName() + " - Test Case FAILED due to Timeout", ExtentColor.RED));
        }
    }
}
