package org.springframework.samples.the_ionian_bookshelf.ui.runePage;

import static org.junit.Assert.fail;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class CreateRunePagePositiveUITest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeEach
  public void setUp() throws Exception {
	String pathToGeckoDriver="D:\\Descargas";
	System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
	   driver = new FirefoxDriver();
	    baseUrl = "https://www.google.com/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }

	  @Test
	  public void testNewRunePagePositive() throws Exception {
	    driver.get("http://localhost:8080/");
	    driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("summoner1");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("summoner1");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[4]/a/span[2]")).click();
	    driver.findElement(By.linkText("Add New Rune Page")).click();
	    driver.findElement(By.xpath("//form[@id='add-rune-page-form']/div/div")).click();
	    driver.findElement(By.id("name")).clear();
	    driver.findElement(By.id("name")).sendKeys("New Rune Page 1");
	    new Select(driver.findElement(By.name("mainBranch"))).selectByVisibleText("Precision");
	    driver.findElement(By.xpath("//option[@value='Precision']")).click();
	    new Select(driver.findElement(By.id("secondaryBranch"))).selectByVisibleText("Domination");
	    driver.findElement(By.xpath("(//option[@value='Domination'])[2]")).click();
	    new Select(driver.findElement(By.id("select 0.0"))).selectByVisibleText("Press the Attack");
	    driver.findElement(By.xpath("//option[@value='Press the Attack']")).click();
	    new Select(driver.findElement(By.id("select 0.25"))).selectByVisibleText("Triumph");
	    driver.findElement(By.xpath("//option[@value='Triumph']")).click();
	    new Select(driver.findElement(By.id("select 0.5"))).selectByVisibleText("Legend: Tenacity");
	    driver.findElement(By.xpath("//option[@value='Legend: Tenacity']")).click();
	    new Select(driver.findElement(By.id("select 0.75"))).selectByVisibleText("Cut Down");
	    driver.findElement(By.xpath("//option[@value='Cut Down']")).click();
	    new Select(driver.findElement(By.id("sec1_1_sel"))).selectByVisibleText("Cheap Shot");
	    driver.findElement(By.xpath("(//option[@id='1'])[4]")).click();
	    new Select(driver.findElement(By.id("sec2_1_sel"))).selectByVisibleText("Ghost Poro");
	    driver.findElement(By.xpath("(//option[@value='Ghost Poro'])[3]")).click();
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
