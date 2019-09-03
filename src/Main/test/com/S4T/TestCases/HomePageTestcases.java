package com.S4T.TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.S4T.Helpers.HomePage;
import com.S4T.Utility.BaseClass;
import com.S4T.Utility.ProvisingConstants;

public class HomePageTestcases extends BaseClass {
	HomePage homePage;

	@Test
	public void sendMessageToContactPerson() throws Exception {
		test = reporter.createTest("verify Message To ContactPerson");
		homePage = new HomePage(driver);
		homePage.clickOnMegaMenu(ProvisingConstants.downloads);
		homePage.contactPeson("saikumar", "bhimanasai7@gmail.com",
				"7416690390", "I have a query");
	}
	
	@Test
	public void verifyResumeWidgetTittle(){
		test = reporter.createTest("verify resume widget Title");
		String resumeTitle = homePage.getResumeWidgetText();
		System.out.println(resumeTitle);
		Assert.assertEquals(resumeTitle, "NAGESH NOW WITH");
	}
}
