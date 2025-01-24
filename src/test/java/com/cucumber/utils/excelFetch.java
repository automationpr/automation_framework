package com.cucumber.utils;

import java.io.IOException;
import java.util.Map;

public class excelFetch {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ExcelUtils.loadExcel("src/test/resources/testdata/TestData.xlsx");
		Map<String, String> testCaseData = ExcelUtils.getTestCaseData("Sheet1", "TC001");
        System.out.println("TestId: " + testCaseData.get("TestId"));
        Map<String, String> data=ExcelUtils.parseTestData(testCaseData.get("TestData"));
        System.out.println(data.get("id"));
        
		
	}

}
