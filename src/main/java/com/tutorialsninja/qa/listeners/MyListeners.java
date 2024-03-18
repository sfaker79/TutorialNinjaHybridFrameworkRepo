package com.tutorialsninja.qa.listeners;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.tutorialsninja.qa.utils.ExtentReporter;

public class MyListeners implements ITestListener{
	
	ExtentReports extentReport;
	ExtentTest extentTest;
	
	@Override
	public void onStart(ITestContext context) {
		
//		System.out.println("> Execution of Project Tests started");
		
		extentReport = ExtentReporter.generateExtentReport();
	}

	@Override
	public void onTestStart(ITestResult result) {
		
		String testName = result.getName();
		
		extentTest  = extentReport.createTest(testName);
		extentTest.log(Status.INFO, testName + " -- started executing");
		
//		System.out.println(testName + " -- started executing");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
		String testName = result.getName();
		extentTest.log(Status.PASS, testName + " -- got successfully executed");
		
//		System.out.println(testName + " -- got successfully executed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		String testName = result.getName();
		
//		System.out.println("**** ScreenShot taken *****");
		
		WebDriver driver = null;
		
		try {
			driver = (WebDriver)result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			
			e.printStackTrace();
		}
		
		File srcScreenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String destinationScreenshotPath = System.getProperty("user.dir")+"\\Screenshots\\"+testName+".png";
		try {
			FileHandler.copy(srcScreenshot, new File(destinationScreenshotPath));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		extentTest.addScreenCaptureFromPath(destinationScreenshotPath);
		extentTest.log(Status.INFO, result.getThrowable());
		extentTest.log(Status.FAIL, testName + " -- got failed");
		
//		System.out.println(testName + " -- got failed");
//		System.out.println(result.getThrowable());
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		
		String testName = result.getName();
		
//		System.out.println(result.getThrowable());
		extentTest.log(Status.INFO, result.getThrowable());
		
//		System.out.println(testName + " -- got skipped");
		extentTest.log(Status.SKIP, testName + " -- got skipped");
				
	}
	
	@Override
	public void onFinish(ITestContext context) {
		
//		System.out.println("> Finished executing Project Tests");
		
		extentReport.flush();
		
//		Auto-launching Extent Report
		
		String pathOfExtentReport = System.getProperty("user.dir")+"\\test-output\\ExtentReports\\extentReport.html";
		File extentReportFile = new File(pathOfExtentReport);
		try {
			Desktop.getDesktop().browse(extentReportFile.toURI());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

}
