package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseTest;
import utilities.Retry;

public class TC002_LoginTest extends BaseTest {
	@Test(groups = { "Sanity", "Master" }, retryAnalyzer = Retry.class)
	public void verifyLogin() throws InterruptedException {
		logger.info("Test started...");
		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			Thread.sleep(3000);

			LoginPage lp = new LoginPage(driver);
			lp.setEmail(property.getProperty("email"));
			lp.setPassword(property.getProperty("password"));
			lp.clickLogin();
			Thread.sleep(3000);

			MyAccountPage map = new MyAccountPage(driver);
			boolean targetPage = map.isMyAccountPageExists();
			Assert.assertEquals(targetPage, true, "Login failed");
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		logger.info("Test ended...");

	}
}
