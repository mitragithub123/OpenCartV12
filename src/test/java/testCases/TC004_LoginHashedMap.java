package testCases;

import java.util.HashMap;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseTest;
import utilities.DataProviders;

public class TC004_LoginHashedMap extends BaseTest {
	/*Valid data
	Testcase1: Login success-->Test pass-->Logout action
	Testcase2: Login unsuccess-->Test fail

	Invalid data
	Testcase3: Login success-->Test fail-->Logout action
	Testcase4: Login unsuccess-->Test pass */
	
	@Test(dataProvider = "LoginDataHashMap", dataProviderClass = DataProviders.class, groups = "Datadriven")
	public void verifyLogin(HashMap<String, String> input) throws InterruptedException {
		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			Thread.sleep(3000);

			LoginPage lp = new LoginPage(driver);
			lp.setEmail(input.get("email"));
			lp.setPassword(input.get("password"));
			lp.clickLogin();
			Thread.sleep(5000);

			MyAccountPage map = new MyAccountPage(driver);
			boolean targetPage = map.isMyAccountPageExists();

			String expectedResult;
			if (input.get("email").isEmpty() || input.get("password").isEmpty()) {
				expectedResult = "Invalid";
			} else {
				expectedResult = "Valid";
			}

			if (expectedResult.equalsIgnoreCase("Valid")) {
				if (targetPage == true) {
					hp.clickMyAccount();
					map.clickLogoutLink();
					Assert.assertTrue(true);
				} else {
					Assert.assertTrue(false);
				}
			} else if (expectedResult.equalsIgnoreCase("Invalid")) {
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
