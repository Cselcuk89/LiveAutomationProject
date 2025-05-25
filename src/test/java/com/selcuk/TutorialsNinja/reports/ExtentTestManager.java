package com.selcuk.TutorialsNinja.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

/**
 * ExtentTestManager class is responsible for managing {@link ExtentTest} objects
 * on a per-thread basis using {@link ThreadLocal}. This allows for thread-safe
 * logging of test steps in parallel test execution environments.
 * It interacts with {@link ExtentManager} to get the global {@link ExtentReports} instance.
 */
public class ExtentTestManager {
    private static ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();
    private static ExtentReports extent = ExtentManager.getInstance();

    /**
     * Returns the {@link ExtentTest} instance for the current thread.
     * This allows test steps and information to be logged to the correct test entry in the report.
     *
     * @return The current thread's {@link ExtentTest} instance.
     */
    public static synchronized ExtentTest getTest() {
        return extentTestThreadLocal.get();
    }

    /**
     * Creates a new test entry in the Extent Report and associates it with the current thread.
     * This method should be called at the beginning of each test case, typically in a test listener's
     * `onTestStart` method.
     *
     * @param testName The name of the test.
     * @param desc A short description of the test.
     * @return The created {@link ExtentTest} instance for the current test.
     */
    public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = extent.createTest(testName, desc);
        extentTestThreadLocal.set(test);
        return test;
    }

    /**
     * Placeholder method, originally intended for actions at the end of a test.
     * Currently, flushing of the ExtentReports instance is handled by {@link ExtentManager#flushInstance()},
     * typically called from a test listener's `onFinish` method for the entire suite.
     * Individual test cleanup related to ExtentTest (like removing from ThreadLocal) could be added here
     * if necessary, but is often managed by the listener's lifecycle methods.
     */
    public static synchronized void endTest() {
        // ExtentReports.getTest().getExtent().flush(); // Original line, but flushing is typically suite-level.
        // If you want to remove the test from thread local after it's done:
        // extentTestThreadLocal.remove();
    }
}
