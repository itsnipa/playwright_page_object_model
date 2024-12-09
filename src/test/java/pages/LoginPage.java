package pages;

import java.nio.file.Paths;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;

import utilities.CommonMethods;

public class LoginPage extends CommonMethods{
	
	Page page;
	ExtentTest test;
	
	ElementHandle username;
	ElementHandle password;
	ElementHandle login_button;

	// Constructor to initialize the page and test objects, and locate the login
	public LoginPage(Page page, ExtentTest test) {
		// Initialize the Page object from the parameter
		this.page = page;
		// Initialize the ExtentTest object from the parameter
		this.test = test;
		
		this.username = page.querySelector("//input[@name='username']");
		this.password = page.querySelector("//input[@name='password1']");
		this.login_button = page.querySelector("//button[@type='submit']");
	}
	
	// Method to log a success message with ExtentReports
		public void handlePass(String message) {
			// Log a message with green color indicating a pass
			test.pass("<p style=\"color:#85BC63; font-size:13px\"><b>" + message + "</b></p>");
		}

		// Method to log a success message and capture a screenshot
		public void handlePassWithScreenshot(String message, String screenshotName) {
			// Log a message with green color and include bold formatting
			test.pass("<p style=\"color:#85BC63; font-size:13px\"><b>" + message + "</b></p>");
			// Capture a full-page screenshot and save it to the specified path
			page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./screenshots/" + screenshotName + ".png"))
					.setFullPage(true));
			
			
			// Build the full path for the screenshot file
			String dest = System.getProperty("user.dir") + "/screenshots/" + screenshotName + ".png";
			// Attach the screenshot to the test report
			test.pass(MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
		}

		// Method to log a failure message, capture a screenshot, and log an exception
		public void handleFail(String message, String screenshotName) {
			// Log a failure message with red color indicating an error
			test.fail("<p style=\"color:#FF5353; font-size:13px\"><b>" + message + "</b></p>");
			// Create an exception and log it in the report
			Throwable t = new InterruptedException("Exception");
			test.fail(t);
			// Capture a full-page screenshot in case of failure and save it to the
			// specified path
			page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./screenshots/" + screenshotName + ".png"))
					.setFullPage(true));
			// Build the full path for the screenshot file
			String dest = System.getProperty("user.dir") + "/screenshots/" + screenshotName + ".png";
			// Attach the screenshot to the failure report
			test.fail(MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
		}
	
		public void login() {
			test.info("This is a login method");
			try {
				if(username.isVisible()) {
					test.info("Please enter your username.");
					username.fill("Admin");
					Thread.sleep(2000);
					handlePass("You had successfully entered your username");
					try {
						if(password.isVisible()) {
							test.info("Please enter your password.");
							password.fill("admin123");
							Thread.sleep(2000);
							handlePass("You had successfully entered your password");
							try {
								test.info("Please click on Login Button.");
								login_button.click();
								Thread.sleep(5000);
								handlePassWithScreenshot("You had successfully logged in", "login_success");
							} catch (Exception e) {
								handleFail("Login Button was not locateable. Please check the error message.", "login_button_fail");
							}
						}
					}catch (Exception e){
						handleFail("Password was not locateable. Please check the error message.", "password_fail");
					}
				}
			}catch (Exception e){
				handleFail("Username was not locateable. Please check the error message.", "username_fail");
			}
		}


}
