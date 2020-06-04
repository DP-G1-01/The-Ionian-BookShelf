package org.springframework.samples.the_ionian_bookshelf.ui.message;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class DeleteMessageAdmindAndReviewerUITest {

	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeEach
	public void setUp() throws Exception {
		String pathToGeckoDriver = "C:\\Users\\Raikonen\\Descargas";
		System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
		driver = new FirefoxDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@DisplayName("Message's delete UI Test")
	@ParameterizedTest(name = "\"{0}\": Message´s deleter")
	@CsvSource({ "admin,admin", "reviewer1,reviewer1" })
	public void testDeleteMessageAdminAndReviewer(String username, String password) throws Exception {
		driver.get("http://localhost:8080/");

		// We create two messages to delete them
		this.createMessage();
		driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[7]/a/span[2]")).click();
		if (username.equals("admin")) {
			driver.findElement(By.linkText("Basic Thread")).click();
			driver.findElement(By.linkText("Remove Message")).click();
			driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li[3]/a/strong")).click();
			driver.findElement(By.linkText("Logout")).click();
			driver.findElement(By.xpath("//button[@type='submit']")).click();
		} else if (username.equals("reviewer1")) {
			driver.findElement(By.linkText("Basic Thread")).click();
			driver.findElement(By.linkText("Remove Message")).click();
			driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li[2]/a/strong")).click();
			driver.findElement(By.linkText("Logout")).click();
			driver.findElement(By.xpath("//button[@type='submit']")).click();
		}
	}

	private void createMessage() {
		driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("summoner1");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("summoner1");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[7]/a/span[2]")).click();
		driver.findElement(By.linkText("Basic Thread")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Add\n			New Message on this Thread')]")).click();
		driver.findElement(By.id("text")).click();
		driver.findElement(By.id("text")).clear();
		driver.findElement(By.id("text")).sendKeys("New Message due to UI Testing");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li[2]/a/strong")).click();
		driver.findElement(By.linkText("Logout")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	@AfterEach
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
