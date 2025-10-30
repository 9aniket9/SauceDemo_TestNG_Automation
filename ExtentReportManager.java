package com.saucedemo.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {
	private static ExtentReports extent;

	public static ExtentReports getReportInstance() {
		if (extent == null) {
			ExtentSparkReporter reporter = new ExtentSparkReporter("test-output/ExtentReport.html");
			reporter.config().setReportName("SauceDemo TestNG Report");
			reporter.config().setDocumentTitle("Automation Results");

			extent = new ExtentReports();
			extent.attachReporter(reporter);
			extent.setSystemInfo("Tester", "Aniket");
			extent.setSystemInfo("Environment", "QA");
		}
		return extent;
	}
}
