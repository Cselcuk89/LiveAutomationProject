package com.selcuk.TutorialsNinja.utils;

import com.aventstack.extentreports.ExtentReports;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ExtentSparkReporterConfig;
import org.openqa.selenium.OutputType;
import org.apache.commons.io.FileUtils; // For FileUtils.copyFile
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
// Removed: import org.openqa.selenium.io.FileHandler; as FileUtils will be used
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

import javax.imageio.ImageIO;
import java.text.SimpleDateFormat; // Ensure SimpleDateFormat is imported
import jakarta.mail.BodyPart; // Updated import
import jakarta.mail.Message; // Updated import
import jakarta.mail.internet.MimeMultipart; // Updated import
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList; // Required for new areItemsInListAreInAscendingOrder
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

/**
 * CommonUtils class provides a collection of static utility methods used across the test automation framework.
 * This includes functionalities such as ExtentReports setup, test data retrieval from Excel,
 * common actions like waiting, email generation, image comparison, screenshot capture, and message parsing.
 */
public class CommonUtils {
    private static final Logger log = LogManager.getLogger(CommonUtils.class);

    /**
     * Retrieves test data for a specific test case from a given sheet in an Excel file.
     * The Excel file is read using {@link MyXLSReader}.
     * Test data is expected to be organized with the test case name in a column, followed by
     * header rows for data parameters and then the data rows.
     *
     * @param xlsReader The {@link MyXLSReader} instance initialized with the Excel file path.
     * @param testName The name of the test case for which to retrieve data.
     * @param sheetName The name of the sheet within the Excel file containing the test data.
     * @return A 2D Object array where each row represents a set of test data for the test case.
     *         Each set is a HashMap where keys are column headers and values are cell data.
     *         Returns an empty array if the test case or sheet is not found, or if an error occurs.
     */
    public static Object[][] getTestData(MyXLSReader xlsReader, String testName, String sheetName) {
        log.info("Executing method: getTestData with parameters: testName={}, sheetName={}", testName, sheetName);

        if (xlsReader == null || xlsReader.workbook == null) {
            log.error("MyXLSReader is not initialized or workbook is null. Cannot get test data for test: {}", testName);
            return new Object[0][0]; // Return empty array if reader is not properly initialized
        }

        int testStartRowNumber = 1;
        while (!(xlsReader.getCellData(sheetName, 1, testStartRowNumber).equals(testName))) {
            testStartRowNumber++;
        }
        log.debug("Found test case {} at row {}", testName, testStartRowNumber);

        int columnStartRowNumber = testStartRowNumber + 1;
        int dataStartRowNumber = testStartRowNumber + 2;

        int rows = 0;
        while (!(xlsReader.getCellData(sheetName, 1, dataStartRowNumber + rows).equals(""))) {
            rows++;
        }
        log.debug("Number of data rows found: {}", rows);

        int columns = 1;
        while (!(xlsReader.getCellData(sheetName, columns, columnStartRowNumber).equals(""))) {
            columns++;
        }
        // The loop for columns will count one extra, so decrement unless it's truly empty.
        if (columns > 1) columns--; // Adjust for 0-based vs 1-based counting or loop condition
        log.debug("Number of data columns found: {}", columns);

        final int effectiveColumns = columns; // Use a final variable for the lambda

        Object[][] obj = new Object[rows][1];

        IntStream.range(0, rows).forEach(i -> {
            HashMap<String, String> map = new HashMap<>();
            IntStream.range(1, effectiveColumns + 1).forEach(j -> { // Iterate from 1 to actual number of columns
                String key = xlsReader.getCellData(sheetName, j, columnStartRowNumber);
                String value = xlsReader.getCellData(sheetName, j, dataStartRowNumber + i);
                map.put(key, value);
                log.trace("Read data for map: key={}, value={}, row={}, col={}", key, value, dataStartRowNumber +i, j);
            });
            obj[i][0] = map;
        });
        log.info("Method getTestData completed. Test data processed for test: {}", testName);
        return obj;
    }

    /**
     * Pauses the current thread execution for a specified number of milliseconds.
     * This is a simple utility for fixed waits, but should be used cautiously in test automation
     * as it can lead to brittle tests. Prefer dynamic waits where possible.
     *
     * @param milliseconds The duration to wait, in milliseconds.
     */
    public static void waitForSeconds(int milliseconds) {
        log.info("Executing method: waitForSeconds with parameters: milliseconds={}", milliseconds);
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            log.error("Thread.sleep interrupted", e);
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
        log.info("Method waitForSeconds completed.");
    }

    /**
     * Converts a string representation of a number to an integer.
     * Logs an error if the string is not a valid integer format.
     *
     * @param text The string to be converted to an integer.
     * @return The integer value of the string, or 0 if conversion fails.
     *         Consider modifying to throw NumberFormatException for clearer error propagation if needed.
     */
    public static int convertToInteger(String text) {
        log.info("Executing method: convertToInteger with parameters: text={}", text);
        int result = 0;
        try {
            result = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            log.error("Error converting text to integer: {}", text, e);
        }
        log.info("Method convertToInteger completed. Returning: {}", result);
        return result;
    }

    /**
     * Checks if a list of strings is sorted in ascending (natural) order.
     *
     * @param list The list of strings to check.
     * @return {@code true} if the list is sorted in ascending order or if the list is null/empty;
     *         {@code false} otherwise.
     */
    public static boolean areItemsInListAreInAscendingOrder(List<String> list) {
        log.info("Executing method: areItemsInListAreInAscendingOrder for list size: {}", list.size());
        if (list == null || list.isEmpty()) {
            log.debug("List is null or empty, considered ascending.");
            return true;
        }
        List<String> sortedList = new ArrayList<>(list);
        Collections.sort(sortedList);
        boolean isEqual = list.equals(sortedList);
        log.info("Method areItemsInListAreInAscendingOrder completed. List is in ascending order: {}", isEqual);
        return isEqual;
    }

    /**
     * Generates a unique email address string based on the current timestamp.
     * The email format is `[timestamp]@example.com`.
     *
     * @return A string representing a unique email address.
     */
    public static String generateBrandNewEmail() {
        log.info("Executing method: generateBrandNewEmail");
        Date date = new Date();
        String dateString = date.toString();
        String dateStringWithoutSpaces = dateString.replaceAll("\\s", "");
        String dateStringWithoutSpacesAndColons = dateStringWithoutSpaces.replaceAll(":", "");
        String brandNewEmail = dateStringWithoutSpacesAndColons + "@example.com";
        log.info("Method generateBrandNewEmail completed. Generated email: {}", brandNewEmail);
        return brandNewEmail;
    }

    /**
     * Compares two images file by file to check if they are different.
     * Uses AShot library for image comparison.
     *
     * @param actualImagePath The file path to the actual image.
     * @param expectedImagePath The file path to the expected image.
     * @return {@code true} if the images are different or if an error occurs during comparison (e.g., file not found);
     *         {@code false} if the images are identical.
     */
    public static boolean compareTwoScreenshots(String actualImagePath, String expectedImagePath) {
        log.info("Executing method: compareTwoScreenshots with parameters: actualImagePath={}, expectedImagePath={}", actualImagePath, expectedImagePath);
        BufferedImage bufferedActualImage = null;
        BufferedImage bufferedExpectedImage = null;
        boolean hasDiff = true; // Default to true (different) in case of errors

        try {
            log.debug("Reading actual image from: {}", actualImagePath);
            bufferedActualImage = ImageIO.read(new File(actualImagePath));
            log.debug("Reading expected image from: {}", expectedImagePath);
            bufferedExpectedImage = ImageIO.read(new File(expectedImagePath));

            if (bufferedActualImage == null || bufferedExpectedImage == null) {
                log.error("One or both images could not be read. Actual: {}, Expected: {}", actualImagePath, expectedImagePath);
                return true; // Indicate difference if images can't be loaded
            }

            ImageDiffer differ = new ImageDiffer();
            ImageDiff imageDiff = differ.makeDiff(bufferedExpectedImage, bufferedActualImage);
            hasDiff = imageDiff.hasDiff();
            log.debug("Image comparison result - hasDiff: {}", hasDiff);

        } catch (IOException e) {
            log.error("IOException during image comparison: actualPath={}, expectedPath={}", actualImagePath, expectedImagePath, e);
            return true; // Consider it different if an error occurs
        }
        log.info("Method compareTwoScreenshots completed. Images are different: {}", hasDiff);
        return hasDiff;
    }

    /**
     * Captures a screenshot of the current browser page.
     * The screenshot is saved in the "test-output/screenshots/" directory with a filename
     * composed of the provided `screenshotName` and a timestamp (e.g., `testName_yyyyMMdd_HHmmss_SSS.png`).
     * The `screenshotName` is sanitized to be filesystem-friendly.
     *
     * @param driver The {@link WebDriver} instance from which to take the screenshot.
     * @param screenshotName A descriptive name for the screenshot, often the test method name.
     * @return The absolute file path to the saved screenshot, or {@code null} if an error occurred
     *         during capture or saving.
     */
    public static String takeScreenshot(WebDriver driver, String screenshotName) {
        log.info("Attempting to take screenshot: {}", screenshotName);
        if (driver == null) {
            log.error("WebDriver instance is null. Cannot take screenshot.");
            return null;
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
        // Sanitize screenshotName to be used in a filename
        String sanitizedScreenshotName = screenshotName.replaceAll("[^a-zA-Z0-9-_\\.]", "_");
        String fileName = sanitizedScreenshotName + "_" + timestamp + ".png";
        // Corrected directory to match task description
        String screenshotDir = System.getProperty("user.dir") + File.separator + "test-output" + File.separator + "screenshots";
        String filePath = screenshotDir + File.separator + fileName;

        try {
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destDir = new File(screenshotDir);
            if (!destDir.exists()){
                if (destDir.mkdirs()) {
                     log.info("Screenshots directory created: {}", screenshotDir);
                } else {
                    log.error("Failed to create screenshots directory: {}", screenshotDir);
                    return null; // Cannot save if directory creation fails
                }
            }
            FileUtils.copyFile(screenshotFile, new File(filePath)); // Using FileUtils from commons-io
            log.info("Screenshot saved successfully: {}", filePath);
            return filePath;
        } catch (IOException e) {
            log.error("IOException while taking screenshot '{}': {}", screenshotName, e.getMessage(), e);
        } catch (Exception e) {
            // Catching generic Exception for other potential issues (e.g., security manager restrictions)
            log.error("Unexpected error while taking screenshot '{}': {}", screenshotName, e.getMessage(), e);
        }
        return null; // Return null if any error occurs
    }

    /**
     * Extracts text content from a {@link Message} object.
     * Handles messages of type "text/plain", "text/html", and "multipart/*".
     * For multipart messages, it recursively calls {@link #getTextFromMimeMultipart(MimeMultipart)}.
     *
     * @param message The {@link Message} object from which to extract text.
     * @return A string containing the extracted text content. Returns an empty string if the message is null
     *         or if content cannot be extracted.
     * @throws Exception if there's an error accessing message content (e.g., {@link IOException}, {@link javax.mail.MessagingException}).
     */
    public static String getTextFromMessage(Message message) throws Exception {
        log.info("Executing method: getTextFromMessage");
        String result = "";
        if (message == null) {
            log.warn("Message object is null.");
            return "";
        }

        try {
            if (message.isMimeType("text/plain")) {
                result = message.getContent().toString();
                log.debug("Message is text/plain. Content extracted.");
            } else if (message.isMimeType("text/html")) {
                result = message.getContent().toString();
                log.debug("Message is text/html. Content extracted.");
            } else if (message.isMimeType("multipart/*")) {
                log.debug("Message is multipart. Processing parts.");
                MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
                result = getTextFromMimeMultipart(mimeMultipart);
            } else {
                log.warn("Unsupported message MIME type: {}", message.getContentType());
            }
        } catch (Exception e) {
            log.error("Error extracting text from message", e);
            throw e;
        }
        log.info("Method getTextFromMessage completed.");
        return result;
    }

    /**
     * Recursively extracts text content from a {@link MimeMultipart} object.
     * Iterates through each {@link BodyPart} and appends its content if it's "text/plain" or "text/html".
     * If a body part itself is a MimeMultipart, it calls itself recursively.
     *
     * @param mimeMultipart The {@link MimeMultipart} object to process.
     * @return A string containing the combined text content from all relevant parts.
     * @throws Exception if there's an error accessing body part content.
     */
    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
        log.info("Executing private method: getTextFromMimeMultipart");
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();
        log.debug("MimeMultipart has {} parts.", count);

        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            log.debug("Processing part {} with MIME type: {}", i, bodyPart.getContentType());
            if (bodyPart.isMimeType("text/plain")) {
                result.append(bodyPart.getContent().toString());
                log.trace("Appended text/plain content from part {}", i);
            } else if (bodyPart.isMimeType("text/html")) {
                // Potentially parse HTML to plain text here if only text is desired
                result.append(bodyPart.getContent().toString()); // For now, appends HTML as is
                log.trace("Appended text/html content from part {}", i);
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                log.debug("Part {} is another MimeMultipart, processing recursively.", i);
                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }
        log.info("Private method getTextFromMimeMultipart completed.");
        return result.toString();
    }
}
