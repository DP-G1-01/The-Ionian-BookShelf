package org.springframework.samples.the_ionian_bookshelf.ui.build;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BuildRemoveUITest {
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
	public void testBuildDeleteUI() throws Exception {
		driver.get("http://localhost:" + port);
		driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("summoner2");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("summoner2");
		driver.findElement(By.xpath("//html")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'My Builds')]")).click();
		assertEquals("Build publica2",
				driver.findElement(By.xpath("//table[@id='buildTable']/tbody/tr/td")).getText());
		driver.findElement(By.xpath("//a[contains(text(),'Remove')]")).click();
		assertNotEquals("Build publica2",
				driver.findElement(By.xpath("//table[@id='buildTable']/tbody/tr/td")).getText());
	}

	@Test
	public void testBuildCantDeleteInPublicViewUI() throws Exception {
		driver.get("http://localhost:" + port);
		driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("summoner2");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("summoner2");
		driver.findElement(By.xpath("//html")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[6]/a/span[2]")).click();
		assertNotEquals("Show / Edit / Remove",
				driver.findElement(By.xpath("//table[@id='buildTable']/tbody/tr/td[8]")).getText());
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
