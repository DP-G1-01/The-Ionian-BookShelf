package org.springframework.samples.the_ionian_bookshelf.ui;

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


public class CreateNewChampionWithFormError {
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
  public void testCreateNewChampionWithFormError() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("admin");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("admin");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/a/span[2]")).click();
    driver.findElement(By.linkText("Add New Champion")).click();
    driver.findElement(By.id("description")).click();
    driver.findElement(By.id("description")).clear();
    driver.findElement(By.id("description")).sendKeys("asdasdad");
    driver.findElement(By.id("health")).click();
    driver.findElement(By.id("health")).clear();
    driver.findElement(By.id("health")).sendKeys("1200");
    driver.findElement(By.id("mana")).click();
    driver.findElement(By.id("mana")).clear();
    driver.findElement(By.id("mana")).sendKeys("1000");
    driver.findElement(By.id("attack")).click();
    driver.findElement(By.id("attack")).clear();
    driver.findElement(By.id("attack")).sendKeys("1");
    driver.findElement(By.id("speed")).click();
    driver.findElement(By.id("speed")).clear();
    driver.findElement(By.id("speed")).sendKeys("1.2");
    new Select(driver.findElement(By.name("role"))).selectByVisibleText("Asesino");
    driver.findElement(By.xpath("//option[@value='Asesino']")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//form[@id='add-champion-form']/div/div/div")).click();
    assertEquals("must not be empty", driver.findElement(By.xpath("//form[@id='add-champion-form']/div/div/div/span[2]")).getText());
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).clear();
    driver.findElement(By.id("name")).sendKeys("Nombre champion");
    driver.findElement(By.xpath("//form[@id='add-champion-form']/div/div[4]")).click();
    driver.findElement(By.id("mana")).clear();
    driver.findElement(By.id("mana")).sendKeys("");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//form[@id='add-champion-form']/div/div[5]/div")).click();
    assertEquals("A Champion must have mana or energy", driver.findElement(By.xpath("//form[@id='add-champion-form']/div/div[5]/div/span[2]")).getText());
    driver.findElement(By.id("energy")).click();
    driver.findElement(By.id("energy")).clear();
    driver.findElement(By.id("energy")).sendKeys("500");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//table[@id='championTable']/tbody/tr[13]/td")).click();
    assertEquals("Nombre champion", driver.findElement(By.xpath("//table[@id='championTable']/tbody/tr[13]/td")).getText());
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
