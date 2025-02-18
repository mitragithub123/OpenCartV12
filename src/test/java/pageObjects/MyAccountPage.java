package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {
	public MyAccountPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(css = "h2:nth-child(1)")
	WebElement msgHeading;

	@FindBy(css = "li[class='dropdown open'] li:nth-child(5) a:nth-child(1)")
	WebElement linkLogOut;

	public boolean isMyAccountPageExists() {
		try {
			return (msgHeading.isDisplayed());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public void clickLogoutLink() {
		linkLogOut.click();
	}

}