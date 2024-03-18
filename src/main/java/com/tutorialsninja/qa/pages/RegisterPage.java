package com.tutorialsninja.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage {

	WebDriver driver;

	@FindBy(id = "input-firstname")
	private WebElement firstNameField;

	@FindBy(id = "input-lastname")
	private WebElement lastNameField;

	@FindBy(id = "input-email")
	private WebElement emailAddressField;

	@FindBy(id = "input-telephone")
	private WebElement telephoneField;

	@FindBy(id = "input-password")
	private WebElement passwordField;

	@FindBy(id = "input-confirm")
	private WebElement passworConfirmdField;

	@FindBy(name = "agree")
	private WebElement privacyPolicyCheckBox;

	@FindBy(xpath = "//input[@name='newsletter'][@value='1']")
	private WebElement yesNewsletterOption;

	@FindBy(xpath = "//input[@value='Continue']")
	WebElement continueButton;

	@FindBy(xpath = "//div[contains(@class, 'alert-dismissible')]")
	private WebElement duplicateEmailAdressWarning;

	@FindBy(xpath = "//div[contains(@class, 'alert-dismissible')]")
	private WebElement privacyPolicyWarning;

	@FindBy(xpath = "//input[@id='input-firstname']/following-sibling::div")
	private WebElement firstNameWarning;

	@FindBy(xpath = "//input[@id='input-lastname']/following-sibling::div")
	private WebElement lastNameWarning;

	@FindBy(xpath = "//input[@id='input-email']/following-sibling::div")
	private WebElement emailWarning;

	@FindBy(xpath = "//input[@id='input-telephone']/following-sibling::div")
	private WebElement telephoneWarning;
	
	@FindBy(xpath = "//input[@id='input-password']/following-sibling::div")
	private WebElement passwordWarning;

	public RegisterPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	// Actions

	public String retrievePrivacyPolicyWarning() {

		String privacyPolicyWarningText = privacyPolicyWarning.getText();
		return privacyPolicyWarningText;
	}

	public String retrieveFirstNameWarning() {

		String firstNameWarningText = firstNameWarning.getText();
		return firstNameWarningText;
	}

	public String retrieveLastNameWarning() {

		String lastNameWarningText = lastNameWarning.getText();
		return lastNameWarningText;
	}

	public String retrieveEmailWarning() {

		String EmailWarningText = emailWarning.getText();
		return EmailWarningText;
	}

	public String retrieveTelephoneWarning() {

		String TelephoneWarningText = telephoneWarning.getText();
		return TelephoneWarningText;
	}
	
	public String retrievePasswordWarning() {

		String PasswordWarningText = passwordWarning.getText();
		return PasswordWarningText;
	}

	public void enterFirstName(String firstNameText) {

		firstNameField.sendKeys(firstNameText);
	}

	public void enterLastName(String lastNameText) {

		lastNameField.sendKeys(lastNameText);
	}

	public void enterEmailAddress(String emailText) {

		emailAddressField.sendKeys(emailText);
	}

	public void enterTelephoneNumber(String telephoneText) {

		telephoneField.sendKeys(telephoneText);
	}

	public void enterPassword(String passwordText) {

		passwordField.sendKeys(passwordText);
	}

	public void enterConfirmPassword(String passwordConfirmText) {

		passworConfirmdField.sendKeys(passwordConfirmText);
	}

	public void selectPrivacyPolicy() {

		privacyPolicyCheckBox.click();
	}

	public void selectYesNewsletterOption() {

		yesNewsletterOption.click();
	}

	public AccountSuccessPage clickOnContinueButton() {

		continueButton.click();
		return new AccountSuccessPage(driver);
	}

	public String retrieveDuplicateEmailAddressWarning() {

		String duplicateEmailWarningText = duplicateEmailAdressWarning.getText();
		return duplicateEmailWarningText;
	}

}
