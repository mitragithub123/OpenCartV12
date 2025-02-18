package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseTest;
import utilities.Retry;

public class TC001_AccountRegistrationTest extends BaseTest {
	@Test(groups = { "Regression", "Master" }, retryAnalyzer = Retry.class)
	public void verifyAccountRegistration() {
		logger.info("Test started");
		try {
			HomePage hp = new HomePage(driver);
			Thread.sleep(5000);
			hp.clickMyAccount();
			hp.clickRegister();

			AccountRegistrationPage arp = new AccountRegistrationPage(driver);
			arp.setFirstName(randomString());
			arp.setLastName(randomString());
			arp.setEmail(randomString() + "@gmail.com");
			arp.setTelephone(randomNumber());
			String password = randomAlphaNumeric();
			arp.setPassword(password);
			arp.setConfirmPassword(password);
			arp.clickPrivacyPolicyCheckBox();
			arp.clickContinueBtn();
			String confirmationMsg = arp.getMsgConfirmation();
			if (confirmationMsg.equals("Your Account Has Been Created!")) {
				Assert.assertTrue(true);
				System.out.println(confirmationMsg);
			} else {
				logger.error("Test failed..");
				Assert.assertFalse(false);

			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		logger.info("Test ended");
	}
}
