package com.selcuk.TutorialsNinja.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File; // For File.separator

/**
 * ExtentManager class is responsible for managing the ExtentReports instance throughout the test execution.
 * It ensures that a single instance of ExtentReports is used (Singleton pattern) and handles
 * the creation, configuration, and flushing of the report.
 * Report files are generated with a timestamp in the "test-output/ExtentReports/" directory.
 */
public class ExtentManager {
    private static ExtentReports extent;
    private static final String REPORT_FILE_NAME_PREFIX = "ExtentReport_";
    private static final String REPORT_DIRECTORY = System.getProperty("user.dir") +
                                                  File.separator + "test-output" +
                                                  File.separator + "ExtentReports" +
                                                  File.separator;

    /**
     * Returns the singleton ExtentReports instance. If an instance does not exist,
     * it calls {@link #createInstance()} to create one.
     *
     * @return The singleton {@link ExtentReports} instance.
     */
    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    /**
     * Creates and configures the ExtentReports instance. This method is synchronized
     * and uses double-check locking to ensure thread-safe singleton creation.
     * It sets up the {@link ExtentSparkReporter} with a timestamped file name,
     * report theme, document title, and other configurations.
     * System information such as OS, Java version, and tester name are also added to the report.
     */
    private static synchronized void createInstance() {
        if (extent != null) { // Double-check locking
            return;
        }
        new File(REPORT_DIRECTORY).mkdirs(); // Ensure the report directory exists
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String fileName = REPORT_FILE_NAME_PREFIX + timeStamp + ".html";
        String reportPath = REPORT_DIRECTORY + fileName;

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setTheme(Theme.STANDARD); // Or Theme.DARK
        sparkReporter.config().setDocumentTitle("TutorialsNinja Test Automation Report");
        sparkReporter.config().setEncoding("utf-8");
        sparkReporter.config().setReportName("Test Execution Report");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Optional: Add system information - these could be read from a properties file
        extent.setSystemInfo("Tester Name", System.getProperty("user.name", "DefaultTester"));
        extent.setSystemInfo("Application URL", "http://tutorialsninja.com/demo/"); // Example, can be dynamic
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
    }

    /**
     * Flushes the ExtentReports instance, writing all test information to the report file.
     * This should be called after all tests have finished, typically in an `onFinish`
     * method of a test listener.
     */
    public static void flushInstance() {
        if (extent != null) {
            extent.flush();
        }
    }
}
