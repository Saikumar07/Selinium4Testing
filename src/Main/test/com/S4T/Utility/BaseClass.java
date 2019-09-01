package com.S4T.Utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseClass {
	
	protected static WebDriver driver;
	private static Properties properties;
	private final static String propertyFilePath= ".\\automation.properties";
	private static BufferedReader reader;
	
	static{
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
		 throw new RuntimeException("automation.properties not found at " + propertyFilePath);
		 } 
		 }
	
	@BeforeTest
	public void openBrowser(){
		if(properties.getProperty("browser").equals("chrome")){
			System.setProperty("webdriver.chrome.driver", properties.getProperty("driverPath"));
            driver = new ChromeDriver();
    		driver.manage().window().maximize();
    		driver.get(properties.getProperty("appurl"));
    		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}
	}
	
	@AfterTest
	public void quiiBrowser(){
		driver.quit();
	}
	
	@AfterMethod
	public void getResult(ITestResult result){
		if(result.getStatus() == ITestResult.SUCCESS){
			System.out.println("Success: "+result.getName());
		}else if(result.getStatus() == ITestResult.FAILURE){
			System.out.println("Failed: "+result.getName());
		}
	}
	
	public static void waitForElement(WebElement element){
		try{
		WebDriverWait wait =new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(element));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
