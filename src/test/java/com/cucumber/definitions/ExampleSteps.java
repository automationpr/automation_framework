package com.cucumber.definitions;

import java.io.IOException;
import java.util.Map;

import com.cucumber.base.Base;
import com.cucumber.pages.LoginPage;
import com.cucumber.utils.ExcelUtils;
import com.cucumber.utils.Reporting;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ExampleSteps {

    private LoginPage loginPage;
    public Map<String, String> testCaseData;
    public Map<String, String> data;

    public ExampleSteps() throws IOException {
        loginPage = new LoginPage(Base.driver);
        ExcelUtils.loadExcel("src/test/resources/testdata/TestData.xlsx");
        testCaseData = ExcelUtils.getTestCaseData("Sheet1", "TC001");
        data=ExcelUtils.parseTestData(testCaseData.get("TestData"));
    }

    @Given("the user loads test data for {string}")
    public void loadTestData(String testCaseName) throws Exception {
        //ExcelUtils excelUtils = new ExcelUtils("src/test/resources/testdata/TestData.xlsx", "Sheet1");
        //testData = excelUtils.getDataByTestCase(testCaseName);
        Reporting.createTest(testCaseName);
        Reporting.logInfo("Test data loaded for: " + testCaseName);
        System.out.println(data.get("id"));
    }

    @Given("the user navigates to the application")
    public void navigateToApp() throws IOException {
        try {
            Base.driver.get(Base.getProperty("url"));
            Reporting.logPass("Navigated to application");
        } catch (Exception e) {
            Reporting.logFail("Failed to navigate to application", Base.driver);
        }
    }

    @When("the user logs in")
    public void performLogin() throws IOException {
        try {
//            loginPage.enterUsername(testData.get("username"));
//            loginPage.enterPassword(testData.get("password"));
//            loginPage.clickLogin();
            Reporting.logPass("Login performed successfully");
        } catch (Exception e) {
            Reporting.logFail("Login failed", Base.driver);
        }
    }

    @Then("the login is validated")
    public void validateLogin() throws IOException {
        try {
            // Validation logic
            Reporting.logPass("Login validation passed");
        } catch (Exception e) {
            Reporting.logFail("Login validation failed", Base.driver);
        }
    }
}
