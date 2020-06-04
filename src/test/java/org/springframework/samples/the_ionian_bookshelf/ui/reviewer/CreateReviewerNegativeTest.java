package org.springframework.samples.the_ionian_bookshelf.ui.reviewer;

import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class CreateReviewerNegativeTest {

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

	@Test
	public void testCreateReviewerNegativeEmail() throws Exception {
		driver.get("http://localhost:8080/");
		driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("admin");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Register\n							a Reviewer')]"))
				.click();
		driver.findElement(By.id("user.username")).click();
		driver.findElement(By.id("user.username")).clear();
		driver.findElement(By.id("user.username")).sendKeys("reviewer5");
		driver.findElement(By.id("user.password")).click();
		driver.findElement(By.id("user.password")).clear();
		driver.findElement(By.id("user.password")).sendKeys("reviewer5");
		driver.findElement(By.name("save")).click();
		driver.findElement(By.xpath("//form[@id='actor']/div/div")).click();
		String forma = "no es una dirección de correo bien formada";
		String vacio = "no puede estar vacío";
		List<String> equal = new ArrayList<String>();
		equal.add(forma);
		equal.add(vacio);
		assertTrue(equal.contains(driver.findElement(By.xpath("//form[@id='actor']/div/div/span[2]")).getText()));
	}

	@Test
	public void testCreateReviewerNegativeUsername() throws Exception {
		driver.get("http://localhost:8080/");
		driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("admin");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Register\n							a Reviewer')]"))
				.click();
		driver.findElement(By.id("email")).click();
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("reviewer5@gmail.com");
		driver.findElement(By.id("user.password")).click();
		driver.findElement(By.id("user.password")).clear();
		driver.findElement(By.id("user.password")).sendKeys("reviewer5");
		driver.findElement(By.name("save")).click();
		driver.findElement(By.xpath("//form[@id='actor']/div[2]/div")).click();
		String forma = "el tamaño tiene que estar entre 5 y 32";
		String vacio = "no puede estar vacío";
		List<String> equal = new ArrayList<String>();
		equal.add(forma);
		equal.add(vacio);
		assertTrue(equal.contains(driver.findElement(By.xpath("//form[@id='actor']/div[2]/div/span[2]")).getText()));
	}

	@Test
	public void testCreateReviewerNegativePassword() throws Exception {
		driver.get("http://localhost:8080/");
		driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("admin");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Register\n							a Reviewer')]"))
				.click();
		driver.findElement(By.id("email")).click();
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("reviewer5@gmail.com");
		driver.findElement(By.id("user.username")).click();
		driver.findElement(By.id("user.username")).clear();
		driver.findElement(By.id("user.username")).sendKeys("reviewer5");
		driver.findElement(By.name("save")).click();
		driver.findElement(By.xpath("//form[@id='actor']/div[3]/div")).click();
		String forma = "el tamaño tiene que estar entre 5 y 32";
		String vacio = "no puede estar vacío";
		List<String> equal = new ArrayList<String>();
		equal.add(forma);
		equal.add(vacio);
		assertTrue(equal.contains(driver.findElement(By.xpath("//form[@id='actor']/div[3]/div/span[2]")).getText()));
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
