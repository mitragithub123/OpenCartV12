package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseTest;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentListener implements ITestListener {

	public ExtentSparkReporter htmlReporter;
	public ExtentReports reports;
	public ExtentTest test;
	public List<String> includedGroups;
	public String reportName;

	@Override
	public void onStart(ITestContext context) {
		configureReport();
		includedGroups = context.getCurrentXmlTest().getIncludedGroups();
	}

	private void configureReport() {
		/*
		  Date date = new Date();
		  SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH-mm-ss");
		  String timeStamp = formatter.format(date);
		 */

		String timeStamp = new SimpleDateFormat("dd MMM yyyy HH-mm-ss").format(new Date());
		reportName = "AutomationTestReport-" + timeStamp + ".html";
		String reportPath = System.getProperty("user.dir") + "/reports/" + reportName;;

		// Ensure the Reports directory exists
		File reportsDir = new File(System.getProperty("user.dir") + "/reports");
		if (!reportsDir.exists()) {
			reportsDir.mkdirs();
		}

		htmlReporter = new ExtentSparkReporter(reportPath);
		reports = new ExtentReports();
		reports.attachReporter(htmlReporter);

		// Add system information/environment info to reports
		reports.setSystemInfo("Machine", "AS-PC-007");
		reports.setSystemInfo("Environment", "Staging");
		reports.setSystemInfo("Browser", "Chrome");

		// reports.setSystemInfo("Tester name", "Mitra bhanu");
		reports.setSystemInfo("Tester name", System.getProperty("user.name")); // To extract system name dynamically

		// To display groups in reports
		if (includedGroups != null && !includedGroups.isEmpty()) {
			reports.setSystemInfo("Groups", includedGroups.toString());
		}

		// Configuration to change look and feel of report
		htmlReporter.config().setDocumentTitle("Extent Listener Report");
		htmlReporter.config().setReportName("OpenCart Test Report");
		htmlReporter.config().setTheme(Theme.STANDARD);
	}

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("Name of test method started: " + result.getName());

		// Start capturing console output
		ConsoleOutputCapture.startCapture();
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("Name of test method successfully executed: " + result.getName());

		test = reports.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); // To display groups in reports
		test.log(Status.PASS,
				MarkupHelper.createLabel("Name of the passed test case is: " + result.getName(), ExtentColor.GREEN));

		// Log captured console output
		String consoleOutput = ConsoleOutputCapture.getCapturedOutput();
		if (consoleOutput != null && !consoleOutput.isEmpty()) {
			test.log(Status.PASS, "Console Output: <pre>" + consoleOutput + "</pre>");
		}
		
		// Attach video recording link
		File videoFile = BaseTest.getVideoFile();
		if (videoFile != null && videoFile.exists()) {
			String videoPath = videoFile.getAbsolutePath();
			String videoLink = "<a href='file:///" + videoPath.replace("\\", "/") + "'>View Video</a>";
			test.info("Screen Recording: " + videoLink);
		}

		// Stop capturing console output
		ConsoleOutputCapture.stopCapture();
		ConsoleOutputCapture.clearCapturedOutput();
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("Name of test method failed: " + result.getName());

		test = reports.createTest(result.getTestClass().getName()); // Create entry in HTML report
		test.assignCategory(result.getMethod().getGroups()); // To display groups in reports
		test.log(Status.FAIL,
				MarkupHelper.createLabel("Name of the failed test case is: " + result.getName(), ExtentColor.RED));
		test.log(Status.INFO, result.getThrowable().getMessage());

		try {
			String screenshotPath = BaseTest.captureScreenshot(BaseTest.driver);
			test.addScreenCaptureFromPath(screenshotPath);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		// Attach video recording link
		File videoFile = BaseTest.getVideoFile();
		if (videoFile != null && videoFile.exists()) {
			String videoPath = videoFile.getAbsolutePath();
			String videoLink = "<a href='file:///" + videoPath.replace("\\", "/") + "'>View Video</a>";
			test.info("Screen Recording: " + videoLink);
		}
        
		// Log captured console output
		String consoleOutput = ConsoleOutputCapture.getCapturedOutput();
		if (consoleOutput != null && !consoleOutput.isEmpty()) {
			test.log(Status.FAIL, "Console Output: <pre>" + consoleOutput + "</pre>");
		}

		// Log the exception message
		Throwable exception = result.getThrowable();
		if (exception != null) {
			test.log(Status.FAIL, exception);
		}

		// Stop capturing console output
		ConsoleOutputCapture.stopCapture();
		ConsoleOutputCapture.clearCapturedOutput();
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("Name of test method skipped: " + result.getName());

		test = reports.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); // To display groups in reports
		test.log(Status.SKIP,
				MarkupHelper.createLabel("Name of the skipped test case is: " + result.getName(), ExtentColor.ORANGE));
		
		// Attach video recording link
        File videoFile = BaseTest.getVideoFile();
        if (videoFile != null && videoFile.exists()) {
            String videoPath = videoFile.getAbsolutePath();
            String videoLink = "<a href='file:///" + videoPath.replace("\\", "/") + "'>View Video</a>";
            test.info("Screen Recording: " + videoLink);
        }

		// Stop capturing console output
		ConsoleOutputCapture.stopCapture();
		ConsoleOutputCapture.clearCapturedOutput();
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("On Finished method invoked...");
		reports.flush(); // Ensure information is written to the reporter

		// To open report automatically in browser
		String reportPath = System.getProperty("user.dir") + "\\reports\\" + reportName;
		File extentReport = new File(reportPath);
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// To send the email report via mail
		// Make sure less secured apps options enabled in gmail (Disable 2 factor authorization)
		/*try {
			URL url = new URL("file:///" + reportPath);

			// Create the email message
			ImageHtmlEmail email = new ImageHtmlEmail();
			email.setDataSourceResolver(new DataSourceUrlResolver(url));
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(587); // 465
			email.setStartTLSEnabled(true); // Enable TLS
			email.setAuthenticator(new DefaultAuthenticator("mitrabhanu.prusty@andolasoft.us", "mitra@1234"));// email & password of sender																											 
			email.setFrom("mitrabhanu.prusty@andolasoft.us"); // Sender
			email.setSubject("Automation Test Results");
			// Email body
		    String emailBody = "<html>"
		        + "<body>"
		        + "<p>Dear Sir,</p>"
		        + "<p>I hope this message finds you well.</p>"
		        + "<p>Attached, please find the latest test results report. This report includes all the recent test cases and their results, which were executed as part of our ongoing testing efforts.</p>"
		        + "<p>Should you have any questions or need further details, please do not hesitate to reach out.</p>"
		        + "<p>Best regards,</p>"
		        + "<p>Mitra bhanu Prusty</p>"
		        + "<p>Quality Analyst</p>"
		        + "<p>+91-8249493741</p>"
		        + "</body>"
		        + "</html>";

		    email.setMsg(emailBody);
			email.setMsg(emailBody);
			email.addTo("andolasoft.user133@gmail.com"); // Receiver
			email.addCc("andolasoft.user212@gmail.com"); // CC recipient
			//email.addBcc("bcc-recipient@example.com"); // BCC recipient
			email.attach(url, "extent report", "please check report...");
			email.send(); // send the email
		} catch (Exception e) {
			e.printStackTrace();
		}*/

	}

}
