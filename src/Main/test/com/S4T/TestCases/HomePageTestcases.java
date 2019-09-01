package com.S4T.TestCases;
import org.testng.annotations.Test;

import com.S4T.Helpers.HomePage;
import com.S4T.Utility.BaseClass;

public class HomePageTestcases extends BaseClass{
	HomePage homePage;
	
	@Test
	public void sendMessageToContactPerson() throws Exception {
		homePage = new HomePage(driver);
		homePage.clickOnMegaMenu("Downloads");
		homePage.contactPeson("saikumar", "bhimanasai7@gmail.com", "7416690390", "I have a query");
		Thread.sleep(5000);
	}
}
