package com.tutorialsninja.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.tutorialsninja.qa.base.Base;
import com.tutorialsninja.qa.pages.AccountPage;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.LoginPage;
import com.tutorialsninja.qa.utils.Utilities;

public class LoginTest extends Base {

	public LoginTest() {
		super();
	}

	public WebDriver driver;
	LoginPage loginPage;
	AccountPage accountPage;

	@BeforeMethod
	public void setup() {

		driver = initializeBrowserAndOpenApplicaionURL(prop.getProperty("browserName"));
		
		HomePage homePage = new HomePage(driver);
		homePage.clickOnMyAccount();
		loginPage = homePage.selectLoginOption();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(priority = 1, dataProvider = "validCredentialsSupplier")
	public void verifyLoginWithValidCredentials(String email, String password) {

		loginPage.enterEmailAddress(email);
		loginPage.enterPassword(password);
		accountPage = loginPage.clickOnLoginButton();
	
		Assert.assertTrue(accountPage.getDisplayStatusOfEditYourAccountInformationOption(),
				"Edit Your Account Information option is not displayed");
	}

	@DataProvider(name = "validCredentialsSupplier")
	public Object[][] supplyTestData() {

		Object[][] data = Utilities.getTestDataFromExcel("Login");
		return data;
	}

	@Test(priority = 2)
	public void verifyLoginWithInvalidCredentials() {

		loginPage.enterEmailAddress(Utilities.generateEmailWithTimeStamp());
		loginPage.enterPassword(dataProp.getProperty("invalidPassword"));
		loginPage.clickOnLoginButton();

		String actualWarningMessage = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String expectedWarningMessage = dataProp.getProperty("emailPasswordNoMatchWarning");

		Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage),
				"Expected Warning message is not displayed");

	}

	@Test(priority = 3)
	public void verifyLoginWithInvalidEmailAndValidPassword() {

		loginPage.enterEmailAddress(Utilities.generateEmailWithTimeStamp());
		loginPage.enterPassword(prop.getProperty("validPassword"));
		loginPage.clickOnLoginButton();

		String actualWarningMessage = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String expectedWarningMessage = dataProp.getProperty("emailPasswordNoMatchWarning");

		Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage),
				"Expected Warning message is not displayed");

	}

	@Test(priority = 4)
	public void verifyLoginWithValidEmailAndInvalidPassword() {
		
		loginPage.enterEmailAddress(prop.getProperty("validEmail"));
		loginPage.enterPassword(dataProp.getProperty("invalidPassword"));
		loginPage.clickOnLoginButton();
		
		String actualWarningMessage = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String expectedWarningMessage = dataProp.getProperty("emailPasswordNoMatchWarning");

		Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage),
				"Expected Warning message is not displayed");

	}

	@Test(priority = 5)
	public void verifyLoginWithoutProvidingCredentials() {

		LoginPage loginPage = new LoginPage(driver);
		loginPage.clickOnLoginButton();
		
		String actualWarningMessage = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String expectedWarningMessage = dataProp.getProperty("emailPasswordNoMatchWarning");

		Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage),
				"Expected Warning message is not displayed");

	}

}
