package com.saucedemo.tests;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	public WebDriver driver;
	public WebDriverWait wait;

	public void openBrowser(String url) {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.get(url);
		System.out.println("Browser Launched Successfully");
	}

	public void closeBrowser() {
		if (driver != null) {
			driver.quit();
			System.out.println("Browser Closed Successfully");
		}
	}
}
