package com.tutorialsninja.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.tutorialsninja.qa.base.Base;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.SearchPage;

public class SearchTest extends Base {

	public SearchTest() {
		super();
	}

	public WebDriver driver;
	SearchPage searchPage;
	HomePage homePage;

	@BeforeMethod
	public void setup() {

		driver = initializeBrowserAndOpenApplicaionURL(prop.getProperty("browserName"));
		homePage = new HomePage(driver);
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(priority = 1)
	public void verifySearchWithValidProduct() {

		homePage.enterProductIntoSearchBoxFiled(dataProp.getProperty("validProduct"));
		searchPage = homePage.clickOnSearchButton();

		Assert.assertTrue(searchPage.displayStatusOfHPValidProduct(),
				"Valid product HP is not displayed in the search results");
	}

	@Test(priority = 2)
	public void verifySearchWithInvalidProduct() {

		homePage.enterProductIntoSearchBoxFiled(dataProp.getProperty("invalidProduct"));
		searchPage = homePage.clickOnSearchButton();

		String actualSearchMessage = searchPage.retrieveNoProductMessageText();
		Assert.assertEquals(actualSearchMessage, dataProp.getProperty("NoProductTextInSearchResults"),
				"No product message in search results is not displayed");
	}

	@Test(priority = 3, dependsOnMethods = {"verifySearchWithValidProduct","verifySearchWithInvalidProduct"})
	public void verifySearchWithoutAnyProduct() {

		searchPage = homePage.clickOnSearchButton();

		String actualSearchMessage = searchPage.retrieveNoProductMessageText();
		Assert.assertEquals(actualSearchMessage, dataProp.getProperty("NoProductTextInSearchResults"),
				"No product message in search results is not displayed");
	}

}
