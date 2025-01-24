package com.cucumber.utils;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.cucumber.base.Base;

public class Reporting {
    private static ExtentReports extent;
    private static ExtentTest test;
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    public static void initReport() {
    	String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String reportPath = "reports/" + Base.getProperty("projectName") + "_TestReport_" + timestamp + ".html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle(Base.getProperty("projectName") + " - Execution Report");
        sparkReporter.config().setReportName(Base.getProperty("projectName") + " - Test Results");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Project Name", Base.getProperty("projectName"));
        extent.setSystemInfo("Execution Date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    public static void createTest(String testName) {
        test = extent.createTest(testName);
        testThread.set(test);
    }

    public static void logPass(String message) {
        testThread.get().pass(message);
    }

    public static void logFail(String message, WebDriver driver) {
        String screenshotPath = captureScreenshot(driver);
       // testThread.get().fail(message).addScreenCaptureFromPath(screenshotPath, "Screenshot");
        testThread.get().fail(message);
        testThread.get().fail("<a href='" + screenshotPath + "' target='_blank'><img src='" + screenshotPath + "' style='width:150px; height:auto;'/></a>");
    }

    public static void logInfo(String message) {
        testThread.get().info(message);
    }

    public static void flushReport() {
        extent.flush();
    }

    private static String captureScreenshot(WebDriver driver) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String returnpath="screenshots/screenshot_" + timestamp + ".png";
        String screenshotPath = "reports/"+returnpath;
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File(screenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnpath;
    }
}