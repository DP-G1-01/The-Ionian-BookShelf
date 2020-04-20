package org.springframework.samples.the_ionian_bookshelf.ui.champion;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
public class UpdateChampionWithoutLoginUITest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeEach
  public void setUp() throws Exception {
	String pathToGeckoDriver="C:\\Users\\fraal\\Downloads";
	System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
    driver = new FirefoxDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testUpdateChampionWithoutLogin() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/a/span[2]")).click();
    driver.findElement(By.linkText("Edit")).click();
    driver.findElement(By.xpath("//html")).click();
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
