package com.saucedemo.tests;

import com.aventstack.extentreports.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.io.File;
import org.apache.commons.io.FileUtils;

public class SauceDemoTest extends BaseTest {

    ExtentReports report;
    ExtentTest test;

    @BeforeClass
    public void setUp() {
        report = ExtentReportManager.getReportInstance();
        test = report.createTest("SauceDemo End-to-End Test");
        openBrowser("https://www.saucedemo.com/");
        test.info("Navigated to SauceDemo website");
    }

    @Test(priority = 1)
    public void loginTest() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name"))).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();

            Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
            test.pass("Login successful");
        } catch (Exception e) {
            captureFailure("loginTest");
            test.fail("Login test failed ❌: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 2)
    public void addToCartTest() {
        try {
            driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
            driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();

            WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
            Assert.assertEquals(cartBadge.getText(), "2");
            test.pass("Added two items to cart successfully");
        } catch (Exception e) {
            captureFailure("addToCartTest");
            test.fail("Add to cart failed ❌: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 3)
    public void checkoutTest() {
        try {
            driver.findElement(By.className("shopping_cart_link")).click();
            driver.findElement(By.id("checkout")).click();
            driver.findElement(By.id("first-name")).sendKeys("Aniket");
            driver.findElement(By.id("last-name")).sendKeys("Tester");
            driver.findElement(By.id("postal-code")).sendKeys("700001");
            driver.findElement(By.id("continue")).click();
            driver.findElement(By.id("finish")).click();

            WebElement confirmation = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("complete-header")));
            Assert.assertEquals(confirmation.getText(), "THANK YOU FOR YOUR ORDER");
            test.pass("Checkout completed successfully");
        } catch (Exception e) {
            captureFailure("checkoutTest");
            test.fail("Checkout test failed ❌: " + e.getMessage());
            throw e;
        }
    }

    @AfterClass
    public void tearDown() {
        closeBrowser();
        report.flush();
        System.out.println("Extent Report Generated: test-output/ExtentReport.html");
    }

    public void captureFailure(String testName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String path = "test-output/screenshots/" + testName + ".png";
            FileUtils.copyFile(src, new File(path));
            test.addScreenCaptureFromPath("screenshots/" + testName + ".png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
