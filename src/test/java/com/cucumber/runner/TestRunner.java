package com.cucumber.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cucumber.base.Base;
import com.cucumber.utils.Reporting;

@CucumberOptions(
    features = "src/test/resources/features", // Path to feature files
    glue = {"com.cucumber.definitions"}, // Path to step definition files
    plugin = {
        "pretty", // Formats the console output for better readability
        "html:target/cucumber-reports.html", // Standard HTML report
        "json:target/cucumber.json", // JSON report for integration with other tools
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" // Extent Reports plugin
    },
    monochrome = true // Displays console output in a readable format
)
@Test
public class TestRunner extends AbstractTestNGCucumberTests {
	
	@BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) {
        if (browser == null || browser.isEmpty()) {
            browser = "chrome"; // Default to Chrome if no parameter is provided
        }
        Base.initializeDriver(browser);
       
    }
	
	 @BeforeClass
	 public void setUpReport() {
		 Reporting.initReport();
	 }
	 
	 @AfterClass
	    public void tearDown1() {
	        // Flush the report after all tests are completed
	        Reporting.flushReport();
	    }

    @AfterMethod
    public void tearDown() {
       // Reporting.flushReport();
        Base.quitDriver();
    }
	
}

