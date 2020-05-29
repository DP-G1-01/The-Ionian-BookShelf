package org.springframework.samples.the_ionian_bookshelf.ui.vote;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
//import org.junit.jupiter.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.service.VoteService;

public class VoteThreadUITest {
	
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeEach
  public void setUp() throws Exception {
	String pathToGeckoDriver="C:\\Users\\pbied\\Downloads";
	System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
    driver = new FirefoxDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }
  
  //Las pruebas de vote las haremos sobre el hilo de ejemplo:
  //summoner1 no debe haber votado en este hilo previamente
 
  
  @Test
  public void testVoteThread() throws Exception {
    driver.get("http://localhost/");
    driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("summoner1");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("summoner1");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[7]/a/span")).click();
 
    //Comprobamos que la puntuación es 0
    assertEquals("0", driver.findElement(By.xpath("//table[@id='threadsTable']/tbody/tr[6]/td[3]")).getText());
    //UpVoteamos
    driver.findElement(By.xpath("//table[@id='threadsTable']/tbody/tr[6]/td[4]/a")).click();
    //Comprobamos que la puntuacion es 1
    assertEquals("1", driver.findElement(By.xpath("//table[@id='threadsTable']/tbody/tr[6]/td[3]")).getText());
    //DownVoteamos 
    driver.findElement(By.xpath("//table[@id='threadsTable']/tbody/tr[6]/td[4]/a[2]")).click();
    //Comprobamos que la puntuaciones es -1. El voto +1 deja de existir -> 0 -1 = -1
    assertEquals("-1", driver.findElement(By.xpath("//table[@id='threadsTable']/tbody/tr[6]/td[3]")).getText());
  }

  @Test
  public void testVoteThreadRepetido() throws Exception{
	  driver.get("http://localhost/");
	    driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("summoner1");
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("summoner1");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[7]/a/span")).click();
	   
	    //Comprobamos que la puntuaciones es -1, debido al test anterior
	    assertEquals("-1", driver.findElement(By.xpath("//table[@id='threadsTable']/tbody/tr[6]/td[3]")).getText());
	    //DownVoteamos 
	    driver.findElement(By.xpath("//table[@id='threadsTable']/tbody/tr[6]/td[4]/a[2]")).click();
	    //Comprobamos error
	    assertEquals("Usted ya emitió este voto previamente.", driver.findElement(By.xpath("//h2")).getText());
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
