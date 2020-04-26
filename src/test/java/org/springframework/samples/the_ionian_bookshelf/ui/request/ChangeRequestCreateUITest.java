package org.springframework.samples.the_ionian_bookshelf.ui.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChangeRequestCreateUITest {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeEach
	public void setUp() throws Exception {
//		String pathToGeckoDriver="/home/blackylyzard/Descargas/";
//		System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "geckodriver");
//	    driver = new FirefoxDriver();
		String pathToGeckoDriver = "C:\\Users\\mitea\\Desktop\\Universidad";
		System.setProperty("webdriver.chrome.driver", pathToGeckoDriver + "\\chromedriver.exe");

		driver = new ChromeDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testChangeRequestCreateUI() throws Exception {
		driver.get("http://localhost:" + port);
		driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("summoner1");
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("summoner1");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[5]/a/span[2]")).click();
		driver.findElement(By.xpath("(//a[contains(text(),'New Change Request')])[2]")).click();
		driver.findElement(By.id("title")).click();
		driver.findElement(By.id("title")).clear();
		driver.findElement(By.id("title")).sendKeys("Change de Item");
		driver.findElement(By.id("description")).click();
		driver.findElement(By.id("description")).clear();
		driver.findElement(By.id("description"))
				.sendKeys("Tiene que tener 20 caracteres asi que hay que escribir bastante.");
		driver.findElement(By.id("changeItem0")).click();
		driver.findElement(By.id("changeItem0")).clear();
		driver.findElement(By.id("changeItem0")).sendKeys("0");
		driver.findElement(By.id("changeItem1")).click();
		driver.findElement(By.id("changeItem1")).clear();
		driver.findElement(By.id("changeItem1")).sendKeys("5");
		driver.findElement(By.id("changeItem2")).click();
		driver.findElement(By.id("changeItem2")).clear();
		driver.findElement(By.id("changeItem2")).sendKeys("3");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/span[2]")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("reviewer1");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("reviewer1");
		driver.findElement(By.id("password")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[8]/a/span[2]")).click();
		assertEquals("Change de Item",
				driver.findElement(By.xpath("//table[@id='requestTable']/tbody/tr[3]/td")).getText());
	}

	@Test
	public void testChangeRequestCreateErrorUI() throws Exception {
		driver.get("http://localhost:" + port);
		driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("summoner1");
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("summoner1");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[5]/a/span[2]")).click();
		driver.findElement(By.xpath("(//a[contains(text(),'New Change Request')])[2]")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("The title can't be empty.",
				driver.findElement(By.xpath("//form[@id='add-change-request-form']/div/div/div/span[2]")).getText());
		assertEquals("The description can't be empty.",
				driver.findElement(By.xpath("//form[@id='add-change-request-form']/div/div[2]/div/span[2]")).getText());
		assertEquals("An attribute can't be empty.",
				driver.findElement(By.xpath("//form[@id='add-change-request-form']/div/div[3]/div/span[2]")).getText());
		assertEquals("An attribute can't be empty.",
				driver.findElement(By.xpath("//form[@id='add-change-request-form']/div/div[4]/div/span[2]")).getText());
		assertEquals("An attribute can't be empty.",
				driver.findElement(By.xpath("//form[@id='add-change-request-form']/div/div[5]/div/span[2]")).getText());
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
