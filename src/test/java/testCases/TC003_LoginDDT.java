package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseTest;
import utilities.DataProviders;
import utilities.Retry;

public class TC003_LoginDDT extends BaseTest{
	/*Valid data
	Testcase1: Login success-->Test pass-->Logout action
	Testcase2: Login unsuccess-->Test fail

	Invalid data
	Testcase3: Login success-->Test fail-->Logout action
	Testcase4: Login unsuccess-->Test pass */
	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups="Datadriven", retryAnalyzer = Retry.class)
	public void verifyLoginDDT(String email, String password, String exp) throws InterruptedException {
		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			Thread.sleep(3000);

			LoginPage lp = new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(password);
			lp.clickLogin();
			Thread.sleep(5000);

			MyAccountPage map = new MyAccountPage(driver);
			boolean targetPage = map.isMyAccountPageExists();

			if (exp.equalsIgnoreCase("Valid")) {
				if (targetPage == true) {
					hp.clickMyAccount();
					map.clickLogoutLink();
					Assert.assertTrue(true);
				} else {
					Assert.assertTrue(false);
				}
			} else if (exp.equalsIgnoreCase("Invalid")) {
				if (targetPage == true) {
					Assert.assertTrue(false);
					hp.clickMyAccount();
					map.clickLogoutLink();
				} else {
					Assert.assertTrue(true);
				}
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}
}
