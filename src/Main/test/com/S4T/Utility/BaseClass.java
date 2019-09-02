package com.S4T.Utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.apache.commons.io.FileUtils;

public class BaseClass {

	protected static WebDriver driver;
	private static Properties properties;
	private final static String propertyFilePath = ".\\automation.properties";
	private static BufferedReader reader;
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports reporter;
	public ExtentTest test;

	static {
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("automation.properties not found at "
					+ propertyFilePath);
		}
	}

	@BeforeTest
	public void createExtentReport() {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")
				+ "/reports/extentReport.html");
		reporter = new ExtentReports();
		htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setReportName("Functional Test");
		htmlReporter.config().setTheme(Theme.DARK);
		reporter.attachReporter(htmlReporter);
	}

	@BeforeMethod
	public void openBrowser() {
		if (properties.getProperty("browser").equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					properties.getProperty("driverPath"));
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.get(properties.getProperty("appurl"));
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}
	}

	@AfterTest
	public void quiiBrowser() {
		reporter.flush();
		driver.quit();
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, "Test case Failed is " + result.getName());
			test.log(Status.FAIL,
					"Test case Failed is " + result.getThrowable());

			String screenshotPath = BaseClass.getScreenshot(result.getName());
			test.addScreenCaptureFromPath(screenshotPath);

		} else if (result.getStatus() == ITestResult.SUCCESS) {
			System.out.println(result.getStatus());
			test.log(Status.PASS, "Test case Passed is " + result.getName());

		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(Status.SKIP, "Test case Skipped is " + result.getName());

		}
	}

	public static void waitForElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public static String getScreenshot(String screenshotName)
			throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss")
				.format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		String destination = System.getProperty("user.dir") + "/Screenshots/"
				+ screenshotName + dateName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

}
