package org.springframework.samples.the_ionian_bookshelf.ui.summoner;

import java.util.concurrent.TimeUnit;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.fail;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class UpdateSummonerNoPasswordUITest {
	  private WebDriver driver;
	  private String baseUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();

	  @BeforeEach
	  public void setUp() throws Exception {
			String pathToGeckoDriver = "D:\\Descargas";
			System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
			driver = new FirefoxDriver();
			baseUrl = "https://www.google.com/";
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }

	  @Test
	  public void testUpdateSummonerNoPasswordUI() throws Exception {
	    driver.get("http://localhost:8080/");
	    driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("summoner1");
	    driver.findElement(By.id("password")).click();
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("summoner1");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[10]/a/span[2]")).click();
	    driver.findElement(By.linkText("Edit")).click();
	    driver.findElement(By.id("leagueId")).click();
	    new Select(driver.findElement(By.id("leagueId"))).selectByVisibleText("Silver");
	    driver.findElement(By.id("leagueId")).click();
	    driver.findElement(By.name("save")).click();
	    driver.findElement(By.id("user.password")).click();
	    driver.findElement(By.id("user.password")).clear();
	    driver.findElement(By.id("user.password")).sendKeys("summoner1");
	    driver.findElement(By.name("save")).click();
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
