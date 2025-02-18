package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {
	public AccountRegistrationPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(css = "#input-firstname")
	WebElement firstName;

	@FindBy(css = "#input-lastname")
	WebElement lastName;

	@FindBy(css = "#input-email")
	WebElement email;

	@FindBy(css = "#input-telephone")
	WebElement telePhone;

	@FindBy(css = "#input-password")
	WebElement password;

	@FindBy(css = "#input-confirm")
	WebElement confirmPassword;

	@FindBy(xpath = "//input[@name='agree']")
	WebElement privacyPolicyCheckBox;

	@FindBy(xpath = "//input[@value='Continue']")
	WebElement continueBtn;

	@FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement msgConfirmation;

	public void setFirstName(String fName) {
		firstName.sendKeys(fName);
	}

	public void setLastName(String lName) {
		lastName.sendKeys(lName);
	}

	public void setEmail(String emailBox) {
		email.sendKeys(emailBox);
	}

	public void setTelephone(String telePh) {
		telePhone.sendKeys(telePh);
	}

	public void setPassword(String pwd) {
		password.sendKeys(pwd);
	}

	public void setConfirmPassword(String pwd) {
		confirmPassword.sendKeys(pwd);
	}

	public void clickPrivacyPolicyCheckBox() {
		privacyPolicyCheckBox.click();
	}

	public void clickContinueBtn() {
		continueBtn.click();
	}

	public String getMsgConfirmation() {
		try {
			return (msgConfirmation.getText());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return (e.getMessage());
		}
	}

}
