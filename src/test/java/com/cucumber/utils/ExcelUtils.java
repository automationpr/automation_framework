package com.cucumber.utils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExcelUtils {

    private static Workbook workbook;

    // Load Excel file
    public static void loadExcel(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(fis);
    }

    // Fetch a row by TestId
    public static Map<String, String> getTestCaseData(String sheetName, String testId) {
        Map<String, String> testDataMap = new HashMap<>();

        Sheet sheet = workbook.getSheet(sheetName);
        Iterator<Row> rows = sheet.iterator();

        // Get the header row
        Row headerRow = rows.next();
        Map<Integer, String> headers = new HashMap<>();
        for (Cell cell : headerRow) {
            headers.put(cell.getColumnIndex(), cell.getStringCellValue());
        }

        // Iterate through rows to find the TestId
        while (rows.hasNext()) {
            Row row = rows.next();
            Cell testIdCell = row.getCell(0); // Assuming TestId is in the first column
            if (testIdCell != null && testIdCell.getStringCellValue().equals(testId)) {
                for (Cell cell : row) {
                    int colIndex = cell.getColumnIndex();
                    String header = headers.get(colIndex);
                    String cellValue = cell.getCellType() == CellType.STRING
                            ? cell.getStringCellValue()
                            : cell.toString();
                    testDataMap.put(header, cellValue);
                }
                break;
            }
        }
        return testDataMap;
    }

    // Parse TestData into key-value pairs
    public static Map<String, String> parseTestData(String testData) {
        Map<String, String> parsedData = new HashMap<>();
        if (testData != null && !testData.isEmpty()) {
            String[] pairs = testData.split(",");
            for (String pair : pairs) {
                String[] keyValue = pair.split(":");
                if (keyValue.length == 2) {
                    parsedData.put(keyValue[0].trim(), keyValue[1].trim());
                }
            }
        }
        return parsedData;
    }

    // Close workbook
    public static void closeExcel() throws IOException {
        if (workbook != null) {
            workbook.close();
        }
    }
}
