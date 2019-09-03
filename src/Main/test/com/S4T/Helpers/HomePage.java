package com.S4T.Helpers;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.S4T.Utility.BaseClass;
import com.S4T.Utility.PageObject;

public class HomePage extends PageObject {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(how = How.ID, using = "closediv")
	private WebElement closePopup;

	@FindBy(how = How.NAME, using = "your-name")
	private WebElement fullname;

	@FindBy(how = How.NAME, using = "your-email")
	private WebElement email_id;

	@FindBy(how = How.NAME, using = "Mobile")
	private WebElement mobile_no;

	@FindBy(how = How.NAME, using = "your-subject")
	private WebElement subject_type;
	
	@FindBy(how = How.XPATH, using = "//*[@class='widget widget_text']/*[@class='widget-title']")
	private WebElement resume_widget_tittle;

	@FindBy(how = How.XPATH, using = "//*[@id='menu-gm']/li[contains(@id,'menu-item')]")
	private List<WebElement> menuItems;

	public void contactPeson(String name, String email, String mobile,
			String subject) {
		BaseClass.waitForElement(fullname);
		fullname.sendKeys(name);
		email_id.sendKeys(email);
		mobile_no.sendKeys(mobile);
		subject_type.sendKeys(subject);
	}

	public void clickOnMegaMenu(String menu_item) throws Exception {
		closePopup.click();
		Thread.sleep(5000);
		int menu_count = menuItems.size();
		for (int i = 0; i < menu_count; i++) {
			String item = menuItems.get(i).getText();
			if (item.equals(menu_item)) {
				System.out.println("Clicking On Menu Item: " + item);
				menuItems.get(i).click();
			}
		}
	}
	
	public String getResumeWidgetText(){
		String widgetText = null;
		widgetText = resume_widget_tittle.getText();
		return widgetText;
	}
}