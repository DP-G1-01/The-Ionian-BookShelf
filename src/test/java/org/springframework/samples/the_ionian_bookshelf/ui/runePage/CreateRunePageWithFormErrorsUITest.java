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

public class CreateRunePageWithFormErrorsUITest {
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
  public void testCreateRunePageWithFormErrorsUI() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
    driver.findElement(By.id("username")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("summoner1");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("summoner1");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[4]/a/span[2]")).click();
    driver.findElement(By.linkText("Add New Rune Page")).click();
    driver.findElement(By.xpath("//body/div")).click();
    driver.findElement(By.id("name")).clear();
    driver.findElement(By.id("name")).sendKeys("");
    new Select(driver.findElement(By.name("mainBranch"))).selectByVisibleText("Domination");
    driver.findElement(By.xpath("//option[@value='Domination']")).click();
    new Select(driver.findElement(By.id("secondaryBranch"))).selectByVisibleText("Precision");
    driver.findElement(By.xpath("(//option[@value='Precision'])[2]")).click();
    new Select(driver.findElement(By.id("select 1.0"))).selectByVisibleText("Predator");
    driver.findElement(By.xpath("//option[@value='Predator']")).click();
    new Select(driver.findElement(By.id("select 1.25"))).selectByVisibleText("Taste of Blood");
    driver.findElement(By.xpath("//option[@value='Taste of Blood']")).click();
    new Select(driver.findElement(By.id("select 1.5"))).selectByVisibleText("Ghost Poro");
    driver.findElement(By.xpath("//option[@value='Ghost Poro']")).click();
    new Select(driver.findElement(By.id("select 1.75"))).selectByVisibleText("Relentless Hunter");
    driver.findElement(By.id("select 1.75")).click();
    new Select(driver.findElement(By.id("sec1_0_sel"))).selectByVisibleText("Triumph");
    driver.findElement(By.xpath("(//option[@id='1'])[2]")).click();
    new Select(driver.findElement(By.id("sec2_0_sel"))).selectByVisibleText("Legend: Tenacity");
    driver.findElement(By.xpath("(//option[@value='Legend: Tenacity'])[3]")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).clear();
    driver.findElement(By.id("name")).sendKeys("New Rune Page 5");
    driver.findElement(By.xpath("//option[@value='Domination']")).click();
    new Select(driver.findElement(By.name("mainBranch"))).selectByVisibleText("Precision");
    driver.findElement(By.xpath("//option[@value='Precision']")).click();
    new Select(driver.findElement(By.id("secondaryBranch"))).selectByVisibleText("Resolve");
    driver.findElement(By.xpath("(//option[@value='Resolve'])[2]")).click();
    new Select(driver.findElement(By.id("select 0.0"))).selectByVisibleText("Lethal Tempo");
    driver.findElement(By.xpath("//option[@value='Lethal Tempo']")).click();
    new Select(driver.findElement(By.id("select 0.25"))).selectByVisibleText("Triumph");
    driver.findElement(By.xpath("//option[@value='Triumph']")).click();
    new Select(driver.findElement(By.id("select 0.5"))).selectByVisibleText("Legend: Tenacity");
    driver.findElement(By.xpath("//option[@value='Legend: Tenacity']")).click();
    new Select(driver.findElement(By.id("select 0.75"))).selectByVisibleText("Last Stand");
    driver.findElement(By.xpath("//option[@value='Last Stand']")).click();
    new Select(driver.findElement(By.id("sec1_2_sel"))).selectByVisibleText("Conditioning");
    driver.findElement(By.xpath("(//option[@id='2'])[7]")).click();
    new Select(driver.findElement(By.id("sec2_2_sel"))).selectByVisibleText("Overgrowth");
    driver.findElement(By.xpath("(//option[@value='Overgrowth'])[3]")).click();
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