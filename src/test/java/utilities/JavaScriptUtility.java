package utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtility {
	public JavascriptExecutor js;

	// Constructor to initialize the JavascriptExecutor
	public JavaScriptUtility(WebDriver driver) {
		this.js = (JavascriptExecutor) driver;
	}

	// Draw border around the element
	public void drawBorderByJs(WebElement element) {
		js.executeScript("arguments[0].style.border='2px solid red'", element);
	}

	// To get the title of the web page
	public String getTitleByJs() {
		return js.executeScript("return document.title").toString();
	}

	// Get page URL using JavaScript
	public String getPageUrl() {
		return (String) js.executeScript("return window.location.href;");
	}

	// To generate an alert
	public void generateAlertByJs(String message) {
		js.executeScript("alert('" + message + "');");
	}

	// Refresh browser
	public void refreshBrowserByJs() {
		js.executeScript("history.go(0)");
	}

	// Browser back
	public void browserBackByJs() {
		js.executeScript("window.history.back()");
	}

	// Browser forward
	public void browserForwardByJs() {
		js.executeScript("window.history.forward()");
	}

	// Scroll to the end of the page
	public void scrollToBottom() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}

	// Scroll to the top of the page
	public void scrollToTop() {
		js.executeScript("window.scrollTo(0, 0);");
	}

	// Scroll to a specific section of the page
	public void scrollToSection(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' });", element);
	}

	// Scroll down by specified pixels
	public void scrollDownByPx(int pixels) {
		js.executeScript("window.scrollBy(0, arguments[0]);", pixels);
	}

	// Scroll up by specified pixels
	public void scrollUpByPx(int pixels) {
		js.executeScript("window.scrollBy(0, arguments[0]);", -pixels);
	}

	// Scroll to a specific X and Y coordinate on the page
	public void scrollToCoordinates(int x, int y) {
		js.executeScript("window.scrollTo(arguments[0], arguments[1]);", x, y);
	}

	// Highlight the element by changing its background color before clicking
	public void highlightElement(WebElement element) {
		js.executeScript("arguments[0].style.backgroundColor = 'yellow';", element);
		try {
			Thread.sleep(200); // Add a slight delay to see the highlight
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		js.executeScript("arguments[0].style.backgroundColor = '';", element); // Revert to original color
	}

	// Click an element using JavaScript
	public void clickElementByJs(WebElement element) {
		js.executeScript("arguments[0].click();", element);
	}

	// Zoom in or out by percentage (e.g., 100 for normal, 150 for zoom-in, 50 for
	// zoom-out)
	public void zoomPageByPercentage(int percentage) {
		js.executeScript("document.body.style.zoom = arguments[0] + '%'", percentage);
	}

	// Flashing an element
	public void changeElementColor(WebDriver driver, WebElement element, String color) {

		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void flashElement(WebDriver driver, WebElement element) {
		String bgColor = element.getCssValue("backgroundColor");
		for (int i = 0; i < 500; i++) {
			changeElementColor(driver, element, "red");
			changeElementColor(driver, element, bgColor);
		}
	}

	// Disable a specific element (e.g., a button or input)
	public void disableElement(WebElement element) {
		js.executeScript("arguments[0].setAttribute('disabled', 'true');", element);
	}

	// Remove an element from the DOM (e.g., an overlay or popup)
	public void removeElementByJs(WebElement element) {
		js.executeScript("arguments[0].parentNode.removeChild(arguments[0]);", element);
	}

}
