package tests;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import basedrivers.BaseDriver;
import pages.LoginPage;
import utilities.ExtentFactory;

public class LoginTest extends BaseDriver {

	ExtentReports report;
	ExtentTest parentTest;
	ExtentTest childTest;

	@BeforeClass
	public void openUrl(@Optional String url, @Optional String browserName, @Optional String headless)
			throws InterruptedException {
		Properties properties = new Properties();
		String env = System.getProperty("env", "dev");
		//String configFileName = String.format(".//resources/config-dev.properties");
		String configFileName = String.format(".//resources/config-dev.properties");
		try (InputStream input = getClass().getClassLoader().getResourceAsStream(configFileName)) {
			if (input == null) {
				System.out.println("Sorry, unable to find " + configFileName);
				return;
			}
			properties.load(input);
			url = url != null ? url : properties.getProperty("url");
			browserName = browserName != null ? browserName : properties.getProperty("browserName");
			headless = headless != null ? headless : properties.getProperty("headless");
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		report = ExtentFactory.getInstance();
		parentTest = report.createTest("<p style=\"color:#FF6000; font-size:20px\"><b>Orange HRM</b></p>")
				.assignAuthor("QA TEAM").assignDevice("Windows");
		launchPlaywright(browserName, headless);
		launchApplication(url);
	}
	
	@Test
	public void testLogin() {
		childTest = parentTest.createNode("<p style=\"color:#3E96E7; font-size:20px\"><b>Login</b></p>");
		LoginPage loginPage = new LoginPage(page, childTest);
		loginPage.login();
	}
	
	@AfterClass
	public void afterClass() {
		closePlaywright();
		report.flush();
	}
	

}
