package com.tutorialsninja.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.tutorialsninja.qa.base.Base;
import com.tutorialsninja.qa.pages.AccountSuccessPage;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.RegisterPage;
import com.tutorialsninja.qa.utils.Utilities;

public class RegisterTest extends Base {
	
	public RegisterTest() {
		super();
	}

	public WebDriver driver;
	RegisterPage registerPage;
	AccountSuccessPage accountSuccessPage;

	@BeforeMethod
	public void setup() {

		driver = initializeBrowserAndOpenApplicaionURL(prop.getProperty("browserName"));
		
		HomePage homePage = new HomePage(driver);
		homePage.clickOnMyAccount();
		registerPage = homePage.selectRegisterOption();
	}

	@AfterMethod
	public void tearDown() {

		driver.quit();
	}

	@Test(priority = 1)
	public void verifyRegisteringAnAccountWithMandatoryFields() {

		registerPage.enterFirstName(dataProp.getProperty("firstName"));
		registerPage.enterLastName(dataProp.getProperty("lastName"));
		registerPage.enterEmailAddress(Utilities.generateEmailWithTimeStamp());
		registerPage.enterTelephoneNumber(dataProp.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validPassword"));
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();

		String actualSuccessHeading = accountSuccessPage.retrieveAccountSuccessPageHeading();

		Assert.assertEquals(actualSuccessHeading, dataProp.getProperty("accountSuccessfullyCreatedHeading"),
				"Account Success page is not displayed");

	}

	@Test(priority = 2)
	public void verifyRegisteringAccountByProvidingAllFields() {

		registerPage.enterFirstName(dataProp.getProperty("firstName"));
		registerPage.enterLastName(dataProp.getProperty("lastName"));
		registerPage.enterEmailAddress(Utilities.generateEmailWithTimeStamp());
		registerPage.enterTelephoneNumber(dataProp.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validPassword"));
		registerPage.selectYesNewsletterOption();
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();
		
		String actualSuccessHeading = accountSuccessPage.retrieveAccountSuccessPageHeading();

		Assert.assertEquals(actualSuccessHeading, dataProp.getProperty("accountSuccessfullyCreatedHeading"),
				"Account Success page is not displayed");

	}

	@Test(priority = 3)
	public void verifyRegisteringAccountWithExistingEmailAddress() {

		registerPage.enterFirstName(dataProp.getProperty("firstName"));
		registerPage.enterLastName(dataProp.getProperty("lastName"));
		registerPage.enterEmailAddress(prop.getProperty("validEmail"));
		registerPage.enterTelephoneNumber(dataProp.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validPassword"));
		registerPage.selectYesNewsletterOption();
		registerPage.selectPrivacyPolicy();
		registerPage.clickOnContinueButton();
		
		String actualWarning = registerPage.retrieveDuplicateEmailAddressWarning();

		Assert.assertTrue(actualWarning.contains(dataProp.getProperty("duplicateEmailWarning")),
				"Warning message regaring duplicate email address is not displayed");

	}

	@Test(priority = 4)
	public void verifyRegisteringAccountWithoutFillingAnyDetails() {

		registerPage.clickOnContinueButton();

		String actualPrivacyPolicyWarning = registerPage.retrievePrivacyPolicyWarning(); 
		Assert.assertTrue(actualPrivacyPolicyWarning.contains(dataProp.getProperty("privacyPolicyWarning")),
				"Pivacy Policy Warning message is nit displayed");

		String actualFirstNameWarning = registerPage.retrieveFirstNameWarning();
		Assert.assertEquals(actualFirstNameWarning, dataProp.getProperty("firstNameWarnning"),
				"First Name Warning message is not displayed");

		String actualLastNameWarning = registerPage.retrieveLastNameWarning();
		Assert.assertEquals(actualLastNameWarning, dataProp.getProperty("lastNameWarning"),
				"Last Name Warning message is not displayed");

		String actualEmailWarning = registerPage.retrieveEmailWarning();
		Assert.assertEquals(actualEmailWarning, dataProp.getProperty("emailWarning"),
				"Email Warning message is not displayed");

		String actualTelephoneWarning = registerPage.retrieveTelephoneWarning();
		Assert.assertEquals(actualTelephoneWarning, dataProp.getProperty("telephoneWarning"),
				"Telephone Warning message is not displayed");

		String actualPasswordWarning = registerPage.retrievePasswordWarning();
		Assert.assertEquals(actualPasswordWarning, dataProp.getProperty("passwordWarning"),
				"Password Warning message is not displayed");

	}

}
