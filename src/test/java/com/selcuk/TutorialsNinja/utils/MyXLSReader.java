package com.selcuk.TutorialsNinja.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File; // For System.getProperty("user.dir") + filepath construction
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.stream.IntStream;

/**
 * MyXLSReader class provides methods to read data from and write data to Microsoft Excel files.
 * It supports both XLS (HSSFWorkbook) and XLSX (XSSFWorkbook) formats.
 * The class handles operations like getting row/column counts, reading/writing cell data,
 * adding/removing sheets and columns, and managing hyperlinks.
 * <p>
 * Note: File I/O operations are performed frequently; for performance-critical applications,
 * consider batching operations or optimizing file access patterns.
 * </p>
 */
public class MyXLSReader {
    private static final Logger log = LogManager.getLogger(MyXLSReader.class);

    /** The absolute path to the Excel file. */
    public String filepath;
    private FileInputStream fis = null; // Renamed for clarity, was just 'fis'
    /** The Apache POI Workbook object. */
    public Workbook workbook = null; // Made public for CommonUtils.getTestData, consider encapsulation
    private Sheet sheet = null;
    private Row row = null;
    private Cell cell = null;
    // public FileOutputStream fileOut = null; // fileOut is now managed locally in methods using try-with-resources

    private String fileExtension = null;

    /**
     * Constructs a MyXLSReader object and initializes the workbook from the given file path.
     * The file path is relative to the project's root directory.
     * It automatically detects whether the file is an XLS or XLSX type.
     *
     * @param relativeFilepath The relative path to the Excel file (e.g., "src/test/resources/data.xlsx").
     */
    public MyXLSReader(String relativeFilepath) {
        log.info("Executing constructor: MyXLSReader with parameters: relativeFilepath={}", relativeFilepath);
        this.filepath = System.getProperty("user.dir") + File.separator + relativeFilepath.replace("/", File.separator);
        log.debug("Normalized absolute filepath: {}", this.filepath);
        this.fileExtension = this.filepath.substring(this.filepath.lastIndexOf("."));

        try {
            fis = new FileInputStream(this.filepath);
            if (fileExtension.equalsIgnoreCase(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
                log.debug("Opened as XLSX workbook.");
            } else if (fileExtension.equalsIgnoreCase(".xls")) {
                workbook = new HSSFWorkbook(fis);
                log.debug("Opened as XLS workbook.");
            } else {
                log.error("Unsupported file extension: {}", fileExtension);
                // Consider throwing an IllegalArgumentException here
                return;
            }
            sheet = workbook.getSheetAt(0); // Default to first sheet
            log.info("MyXLSReader initialized successfully for filepath: {}", this.filepath);
        } catch (Exception e) {
            log.error("Exception initializing MyXLSReader for filepath {}: {}", this.filepath, e.getMessage(), e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                    log.debug("FileInputStream closed for: {}", this.filepath);
                } catch (IOException e) {
                    log.error("IOException closing FileInputStream for filepath {}: {}", this.filepath, e.getMessage(), e);
                }
            }
        }
    }

    /**
     * Gets the total number of rows in a specified sheet.
     *
     * @param sheetName The name of the sheet.
     * @return The number of rows in the sheet, or 0 if the sheet is not found.
     */
    public int getRowCount(String sheetName) {
        log.info("Executing method: getRowCount with parameters: sheetName={}", sheetName);
        int sheetIndex = workbook.getSheetIndex(sheetName);
        if (sheetIndex == -1) {
            log.warn("Sheet '{}' not found. Returning row count 0.", sheetName);
            return 0;
        }
        sheet = workbook.getSheetAt(sheetIndex);
        int rowsCount = sheet.getLastRowNum() + 1;
        log.info("Method getRowCount completed. Sheet: {}, Rows: {}", sheetName, rowsCount);
        return rowsCount;
    }

    /**
     * Retrieves data from a specific cell identified by sheet name, column name, and row number.
     * Row numbers are 1-based. Column names are case-insensitive.
     *
     * @param sheetName The name of the sheet.
     * @param colName   The name of the column.
     * @param rowNum    The row number (1-based).
     * @return The cell data as a String. Returns an empty string if the cell is empty, not found,
     *         or if an error occurs. Returns "Error: [message]" on exception.
     */
    public String getCellData(String sheetName, String colName, int rowNum) {
        log.info("Executing method: getCellData with parameters: sheetName={}, colName={}, rowNum={}", sheetName, colName, rowNum);
        String cellValue = "";
        try {
            if (rowNum <= 0) {
                log.warn("Invalid rowNum: {}. Must be > 0.", rowNum);
                return "";
            }
            int sheetIndex = workbook.getSheetIndex(sheetName);
            if (sheetIndex == -1) {
                log.warn("Sheet '{}' not found.", sheetName);
                return "";
            }
            sheet = workbook.getSheetAt(sheetIndex);
            row = sheet.getRow(0); // Header row
            if (row == null) {
                log.warn("Header row not found in sheet '{}'.", sheetName);
                return "";
            }

            // Use IntStream to find the column number
            final Row headerRow = row; // Effectively final for lambda
            int colNum = IntStream.range(0, headerRow.getLastCellNum())
                .filter(i -> headerRow.getCell(i) != null &&
                               headerRow.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName.trim()))
                .findFirst()
                .orElse(-1);

            if (colNum == -1) {
                log.warn("Column '{}' not found in sheet '{}'.", colName, sheetName);
                return "";
            }

            row = sheet.getRow(rowNum - 1); // Actual data row
            if (row == null) {
                log.debug("Row {} is null in sheet '{}'. No data to retrieve.", rowNum, sheetName);
                return "";
            }
            cell = row.getCell(colNum);
            if (cell == null) {
                log.debug("Cell at row {}, col {} ('{}') is null in sheet '{}'.", rowNum, colNum, colName, sheetName);
                return "";
            }

            cellValue = getCellValueAsString(cell);
            log.debug("Retrieved cell data from sheet: {}, col: {}, row: {}. Value: '{}'", sheetName, colName, rowNum, cellValue);

        } catch (Exception e) {
            log.error("Exception in getCellData (sheet: {}, col: {}, row: {}): {}", sheetName, colName, rowNum, e.getMessage(), e);
            return "Error: " + e.getMessage();
        }
        log.info("Method getCellData completed. Returning: '{}'", cellValue);
        return cellValue;
    }

    /**
     * Retrieves data from a specific cell identified by sheet name, column number, and row number.
     * Both column and row numbers are 1-based.
     *
     * @param sheetName The name of the sheet.
     * @param colNum    The column number (1-based).
     * @param rowNum    The row number (1-based).
     * @return The cell data as a String. Returns an empty string if the cell is empty, not found,
     *         or if an error occurs. Returns "Error: [message]" on exception.
     */
    public String getCellData(String sheetName, int colNum, int rowNum) {
        log.info("Executing method: getCellData with parameters: sheetName={}, colNum={}, rowNum={}", sheetName, colNum, rowNum);
        String cellValue = "";
        try {
            if (rowNum <= 0 || colNum <=0) {
                log.warn("Invalid rowNum: {} or colNum: {}. Must be > 0.", rowNum, colNum);
                return "";
            }
            int sheetIndex = workbook.getSheetIndex(sheetName);
            if (sheetIndex == -1) {
                log.warn("Sheet '{}' not found.", sheetName);
                return "";
            }
            sheet = workbook.getSheetAt(sheetIndex);
            row = sheet.getRow(rowNum - 1);
            if (row == null) {
                log.debug("Row {} is null in sheet '{}'. No data to retrieve.", rowNum, sheetName);
                return "";
            }
            cell = row.getCell(colNum - 1);
            if (cell == null) {
                log.debug("Cell at row {}, col {} is null in sheet '{}'.", rowNum, colNum, sheetName);
                return "";
            }

            cellValue = getCellValueAsString(cell);
            log.debug("Retrieved cell data from sheet: {}, colIndex: {}, rowIndex: {}. Value: '{}'", sheetName, colNum-1, rowNum-1, cellValue);

        } catch (Exception e) {
            log.error("Exception in getCellData (sheet: {}, colIndex: {}, rowIndex: {}): {}", sheetName, colNum-1, rowNum-1, e.getMessage(), e);
            return "Error: " + e.getMessage();
        }
        log.info("Method getCellData completed. Returning: '{}'", cellValue);
        return cellValue;
    }

    /**
     * Helper method to convert cell content to a String representation.
     * Handles different cell types including STRING, NUMERIC (with date formatting),
     * BOOLEAN, FORMULA (evaluates cached result), and BLANK.
     *
     * @param cell The {@link Cell} to process.
     * @return String representation of the cell's value. Returns an empty string for null cells or unsupported types.
     */
    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(DateUtil.getJavaDate(cell.getNumericCellValue()));
                    return (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + String.valueOf(cal.get(Calendar.YEAR)).substring(2);
                } else {
                    double numValue = cell.getNumericCellValue();
                    if (numValue == (long) numValue) {
                        return String.valueOf((long)numValue);
                    } else {
                        return String.valueOf(numValue);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    switch(cell.getCachedFormulaResultType()) {
                        case STRING: return cell.getStringCellValue().trim();
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(DateUtil.getJavaDate(cell.getNumericCellValue()));
                                return (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + String.valueOf(cal.get(Calendar.YEAR)).substring(2);
                            } else {
                                double numValue = cell.getNumericCellValue();
                                if (numValue == (long) numValue) return String.valueOf((long)numValue);
                                else return String.valueOf(numValue);
                            }
                        case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
                        case ERROR: return "FormulaError: " + cell.getErrorCellValue();
                        default: return "";
                    }
                } catch (IllegalStateException e) {
                    log.warn("IllegalStateException for formula cell, trying to evaluate: {}", e.getMessage());
                    return "FormulaEvalError";
                }
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    /**
     * Sets the data in a specific cell identified by sheet name, column name, and row number.
     * If the sheet, row, or column does not exist, it attempts to create them.
     * Row numbers are 1-based.
     *
     * @param sheetName The name of the sheet.
     * @param colName   The name of the column.
     * @param rowNum    The row number (1-based).
     * @param data      The string data to set in the cell.
     * @return {@code true} if data is set successfully, {@code false} otherwise.
     */
    public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
        log.info("Executing method: setCellData with parameters: sheetName={}, colName={}, rowNum={}, data={}", sheetName, colName, rowNum, data);
        try (FileOutputStream fileOutLocal = new FileOutputStream(this.filepath)) {
            if (rowNum <= 0) {
                log.warn("Invalid rowNum: {}. Must be > 0.", rowNum);
                return false;
            }
            int sheetIndex = workbook.getSheetIndex(sheetName);
            if (sheetIndex == -1) {
                log.warn("Sheet '{}' not found.", sheetName);
                return false; // Or create sheet: sheet = workbook.createSheet(sheetName);
            }
            sheet = workbook.getSheetAt(sheetIndex);
            row = sheet.getRow(0);
            if (row == null) {
                log.debug("Header row not found in sheet '{}', creating it.", sheetName);
                row = sheet.createRow(0);
            }

            int colNum = -1;
            for (int i = 0; i < row.getLastCellNum(); i++) {
                if (row.getCell(i) != null && row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName.trim())) {
                    colNum = i;
                    break;
                }
            }
            if (colNum == -1) {
                log.debug("Column '{}' not found, creating it at index {}.", colName, row.getLastCellNum() == -1 ? 0 : row.getLastCellNum());
                colNum = row.getLastCellNum() == -1 ? 0 : row.getLastCellNum();
                Cell headerCell = row.createCell(colNum);
                headerCell.setCellValue(colName);
            }

            row = sheet.getRow(rowNum - 1);
            if (row == null) row = sheet.createRow(rowNum - 1);

            cell = row.getCell(colNum);
            if (cell == null) cell = row.createCell(colNum);

            cell.setCellValue(data);
            log.debug("Set cell data for sheet: {}, col: {}, row: {}. Value: '{}'", sheetName, colName, rowNum, data);

            workbook.write(fileOutLocal);
            log.info("Method setCellData completed. Data written to filepath: {}", this.filepath);
            return true;
        } catch (Exception e) {
            log.error("Exception in setCellData (sheet: {}, col: {}, row: {}, data: {}): {}", sheetName, colName, rowNum, data, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Sets the data and a hyperlink in a specific cell.
     * If the sheet, row, or column does not exist, it attempts to create them.
     * Row numbers are 1-based. Hyperlink type is set to URL.
     *
     * @param sheetName The name of the sheet.
     * @param colName   The name of the column.
     * @param rowNum    The row number (1-based).
     * @param data      The string data/text to display in the cell.
     * @param url       The URL for the hyperlink.
     * @return {@code true} if data and hyperlink are set successfully, {@code false} otherwise.
     */
    public boolean setCellData(String sheetName, String colName, int rowNum, String data, String url) {
        log.info("Executing method: setCellData with hyperlink. Parameters: sheetName={}, colName={}, rowNum={}, data={}, url={}", sheetName, colName, rowNum, data, url);
        try (FileOutputStream fileOutLocal = new FileOutputStream(this.filepath)) {
            if (rowNum <= 0) {
                 log.warn("Invalid rowNum: {}. Must be > 0.", rowNum);
                return false;
            }
            int sheetIndex = workbook.getSheetIndex(sheetName);
             if (sheetIndex == -1) {
                log.warn("Sheet '{}' not found.", sheetName); // Or create sheet
                return false;
            }
            sheet = workbook.getSheetAt(sheetIndex);
            row = sheet.getRow(0);
            if (row == null) row = sheet.createRow(0);


            int colNum = -1;
             for (int i = 0; i < row.getLastCellNum(); i++) {
                if (row.getCell(i) != null && row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName.trim())) {
                    colNum = i;
                    break;
                }
            }
            if (colNum == -1) {
                log.debug("Column '{}' not found, creating it for hyperlink.", colName);
                colNum = row.getLastCellNum() == -1 ? 0 : row.getLastCellNum();
                Cell headerCell = row.createCell(colNum);
                headerCell.setCellValue(colName);
            }
            
            sheet.autoSizeColumn(colNum);
            row = sheet.getRow(rowNum - 1);
            if (row == null) row = sheet.createRow(rowNum - 1);

            cell = row.getCell(colNum);
            if (cell == null) cell = row.createCell(colNum);

            cell.setCellValue(data);
            CreationHelper createHelper = workbook.getCreationHelper();
            Hyperlink link = createHelper.createHyperlink(HyperlinkType.URL);
            link.setAddress(url);
            cell.setHyperlink(link);

            CellStyle hlinkStyle = workbook.createCellStyle();
            Font hlinkFont = workbook.createFont();
            hlinkFont.setUnderline(Font.U_SINGLE);
            hlinkFont.setColor(IndexedColors.BLUE.getIndex());
            hlinkStyle.setFont(hlinkFont);
            cell.setCellStyle(hlinkStyle);

            workbook.write(fileOutLocal);
            log.info("Method setCellData with hyperlink completed. Data and link written to filepath: {}", this.filepath);
            return true;
        } catch (Exception e) {
            log.error("Exception in setCellData with hyperlink (sheet: {}, col: {}, row: {}, data: {}, url: {}): {}", sheetName, colName, rowNum, data, url, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Adds a new sheet to the workbook.
     *
     * @param sheetName The name of the sheet to add.
     * @return {@code true} if the sheet is added successfully, {@code false} if a sheet with the same name
     *         already exists or if an error occurs.
     */
    public boolean addSheet(String sheetName) {
        log.info("Executing method: addSheet with parameters: sheetName={}", sheetName);
        if (workbook.getSheet(sheetName) != null) {
            log.warn("Sheet '{}' already exists. Cannot add.", sheetName);
            return false;
        }
        try (FileOutputStream fileOutLocal = new FileOutputStream(this.filepath)) {
            workbook.createSheet(sheetName);
            workbook.write(fileOutLocal);
            log.info("Method addSheet completed. Sheet '{}' added to filepath: {}", sheetName, this.filepath);
            return true;
        } catch (Exception e) {
            log.error("Exception in addSheet (sheetName: {}): {}", sheetName, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Removes a sheet from the workbook.
     *
     * @param sheetName The name of the sheet to remove.
     * @return {@code true} if the sheet is removed successfully, {@code false} if the sheet does not exist
     *         or if an error occurs.
     */
    public boolean removeSheet(String sheetName) {
        log.info("Executing method: removeSheet with parameters: sheetName={}", sheetName);
        int sheetIndex = workbook.getSheetIndex(sheetName);
        if (sheetIndex == -1) {
            log.warn("Sheet '{}' not found. Cannot remove.", sheetName);
            return false;
        }
        try (FileOutputStream fileOutLocal = new FileOutputStream(this.filepath)) {
            workbook.removeSheetAt(sheetIndex);
            workbook.write(fileOutLocal);
            log.info("Method removeSheet completed. Sheet '{}' removed from filepath: {}", sheetName, this.filepath);
            return true;
        } catch (Exception e) {
            log.error("Exception in removeSheet (sheetName: {}): {}", sheetName, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Adds a new column to the specified sheet. The column is added to the header row (row 0).
     * If the header row does not exist, it is created.
     *
     * @param sheetName The name of the sheet.
     * @param colName   The name of the column to add.
     * @return {@code true} if the column is added successfully, {@code false} if the sheet does not exist,
     *         the column already exists, or an error occurs.
     */
    public boolean addColumn(String sheetName, String colName) {
        log.info("Executing method: addColumn with parameters: sheetName={}, colName={}", sheetName, colName);
        try (FileOutputStream fileOutLocal = new FileOutputStream(this.filepath)) {
            int sheetIndex = workbook.getSheetIndex(sheetName);
            if (sheetIndex == -1) {
                log.warn("Sheet '{}' not found. Cannot add column.", sheetName);
                return false;
            }
            sheet = workbook.getSheetAt(sheetIndex);
            row = sheet.getRow(0);
            if (row == null) row = sheet.createRow(0);

            for(int i=0; i < row.getLastCellNum(); i++) {
                if(row.getCell(i) != null && row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName.trim())) {
                    log.warn("Column '{}' already exists in sheet '{}'. Cannot add.", colName, sheetName);
                    return false;
                }
            }

            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            cell = row.createCell(row.getLastCellNum() == -1 ? 0 : row.getLastCellNum());
            cell.setCellValue(colName);
            cell.setCellStyle(style);

            workbook.write(fileOutLocal);
            log.info("Method addColumn completed. Column '{}' added to sheet '{}' in filepath: {}", colName, sheetName, this.filepath);
            return true;
        } catch (Exception e) {
            log.error("Exception in addColumn (sheetName: {}, colName: {}): {}", sheetName, colName, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Removes a column and its contents from a sheet. This implementation removes the cell
     * from each row at the specified column index, which effectively blanks the cells.
     * It does not shift subsequent columns to the left.
     * Column numbers are 1-based.
     *
     * @param sheetName The name of the sheet.
     * @param colNum    The column number to remove (1-based).
     * @return {@code true} if the column's cells are removed/cleared successfully, {@code false}
     *         if the sheet does not exist, colNum is invalid, or an error occurs.
     */
    public boolean removeColumn(String sheetName, int colNum) {
        log.info("Executing method: removeColumn with parameters: sheetName={}, colNum={}", sheetName, colNum);
        if (colNum <= 0) {
            log.warn("Invalid colNum: {}. Must be > 0.", colNum);
            return false;
        }
        try (FileOutputStream fileOutLocal = new FileOutputStream(this.filepath)) {
            if (!isSheetExist(sheetName)) {
                log.warn("Sheet '{}' does not exist. Cannot remove column.", sheetName);
                return false;
            }
            sheet = workbook.getSheet(sheetName);
            
            for (int i = 0; i < getRowCount(sheetName); i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    Cell cellToRemove = row.getCell(colNum - 1);
                    if (cellToRemove != null) {
                        row.removeCell(cellToRemove);
                        log.debug("Removed cell at row {}, colNum {} in sheet {}", i, colNum-1, sheetName);
                    }
                }
            }
            workbook.write(fileOutLocal);
            log.info("Method removeColumn completed for sheet: {}, colNum: {}. Cells in column cleared/removed.", sheetName, colNum);
            return true;
        } catch (Exception e) {
            log.error("Exception in removeColumn (sheetName: {}, colNum: {}): {}", sheetName, colNum, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Checks if a sheet exists in the workbook. The check is case-insensitive for the sheet name.
     *
     * @param sheetName The name of the sheet to check.
     * @return {@code true} if the sheet exists, {@code false} otherwise.
     */
    public boolean isSheetExist(String sheetName) {
        log.info("Executing method: isSheetExist with parameters: sheetName={}", sheetName);
        boolean exists = workbook.getSheet(sheetName) != null;
        if (!exists) {
            exists = workbook.getSheet(sheetName.toUpperCase()) != null;
        }
        log.info("Method isSheetExist completed. Sheet '{}' exists: {}", sheetName, exists);
        return exists;
    }

    /**
     * Gets the total number of columns in a specified sheet, based on the header row (row 0).
     *
     * @param sheetName The name of the sheet.
     * @return The number of columns in the sheet's header row. Returns -1 if the sheet or header row
     *         is not found. Returns 0 if the header row is empty.
     */
    public int getColumnCount(String sheetName) {
        log.info("Executing method: getColumnCount with parameters: sheetName={}", sheetName);
        if (!isSheetExist(sheetName)) {
            log.warn("Sheet '{}' does not exist. Returning column count -1.", sheetName);
            return -1;
        }
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(0);
        if (row == null) {
            log.warn("Header row not found in sheet '{}'. Returning column count -1.", sheetName);
            return -1;
        }
        int colCount = row.getLastCellNum();
        colCount = (colCount == -1) ? 0 : colCount;
        log.info("Method getColumnCount completed. Sheet: {}, Columns: {}", sheetName, colCount);
        return colCount;
    }

    /**
     * Adds a hyperlink to a cell for a specific test case.
     * Searches for the `testCaseName` in the first column of the `sheetName`.
     * When found, it sets the `message` as the cell text in the `screenShotColName` column
     * for that row and creates a hyperlink to the `url`.
     *
     * @param sheetName The name of the sheet.
     * @param screenShotColName The name of the column where the hyperlink and message should be placed.
     * @param testCaseName The name of the test case to find in the first column.
     * @param url The URL for the hyperlink.
     * @param message The text to display in the cell.
     * @return {@code true} if the hyperlink is added successfully, {@code false} if the sheet or
     *         test case is not found, or an error occurs.
     */
    public boolean addHyperLink(String sheetName, String screenShotColName, String testCaseName, String url, String message) {
        log.info("Executing method: addHyperLink. Parameters: sheetName={}, screenShotColName={}, testCaseName={}, url={}, message={}",
                sheetName, screenShotColName, testCaseName, url, message);
        if (!isSheetExist(sheetName)) {
            log.warn("Sheet '{}' does not exist. Cannot add hyperlink.", sheetName);
            return false;
        }
        for (int i = 1; i <= getRowCount(sheetName); i++) {
            if (getCellData(sheetName, 1, i).equalsIgnoreCase(testCaseName)) {
                log.debug("Found test case '{}' at row {}. Setting hyperlink in column '{}'.", testCaseName, i, screenShotColName);
                boolean result = setCellData(sheetName, screenShotColName, i, message, url);
                log.info("Method addHyperLink completed for testCase '{}'. Hyperlink set: {}", testCaseName, result);
                return result;
            }
        }
        log.warn("Test case '{}' not found in sheet '{}'. Hyperlink not added.", testCaseName, sheetName);
        return false;
    }

    /**
     * Finds the row number for a cell that contains a specific value within a given column.
     * Row numbers are 1-based. Comparison is case-insensitive.
     *
     * @param sheetName The name of the sheet.
     * @param colName   The name of the column to search within.
     * @param cellValue The value to search for in the column.
     * @return The 1-based row number where the value is found. Returns -1 if the value is not found
     *         or if the sheet/column does not exist.
     */
    public int getCellRowNum(String sheetName, String colName, String cellValue) {
        log.info("Executing method: getCellRowNum. Parameters: sheetName={}, colName={}, cellValue={}", sheetName, colName, cellValue);
        for (int i = 1; i <= getRowCount(sheetName); i++) {
            if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
                log.info("Method getCellRowNum completed. Found cell value '{}' in column '{}' at row {}.", cellValue, colName, i);
                return i;
            }
        }
        log.warn("Cell value '{}' not found in column '{}' of sheet '{}'. Returning -1.", cellValue, colName, sheetName);
        return -1;
    }
}
