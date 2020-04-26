package org.springframework.samples.the_ionian_bookshelf.bdd.stepdefinitions;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;

@Log
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemCreationStepDefinitions extends AbstractStep {

	@LocalServerPort
	private int port;
	
	@Given("I am logged as {string}")
	public void IamLoginAs(String username) throws Exception {		
		loginAs(username,passwordOf(username),port, getDriver());		
	}
	
	@When("I go to the item creation form")
	public void goToForm() throws Exception {		
		goToFormAux(port, getDriver());
	}

	@And("Fill the form correctly")
	public  void fillFormCorrect() throws Exception {
		fillForm(getDriver());
	}
	
	public static void loginAs(String username,String password,int port,WebDriver driver) {				
		driver.get("http://localhost:"+port);
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}
	
	private static String passwordOf(String username) {
		String result="admin";
		if("summoner1".equals(username))
			result="summoner1";
		return result;
	}
	
	public static void fillForm(WebDriver driver) throws Exception {		
		  driver.findElement(By.id("title")).click();
		    driver.findElement(By.id("title")).clear();
		    driver.findElement(By.id("title")).sendKeys("ItemTestUI");
		    driver.findElement(By.id("description")).click();
		    driver.findElement(By.id("description")).clear();
		    driver.findElement(By.id("description")).sendKeys("ItemTestUI descripción y tal");
		    driver.findElement(By.id("attributes0")).click();
		    driver.findElement(By.id("attributes0")).clear();
		    driver.findElement(By.id("attributes0")).sendKeys("21");
		    driver.findElement(By.id("attributes1")).click();
		    driver.findElement(By.id("attributes1")).clear();
		    driver.findElement(By.id("attributes1")).sendKeys("12");
		    driver.findElement(By.id("attributes2")).click();
		    driver.findElement(By.id("attributes2")).clear();
		    driver.findElement(By.id("attributes2")).sendKeys("32");
		    new Select(driver.findElement(By.name("roles[0]"))).selectByVisibleText("Tirador");
		    driver.findElement(By.xpath("//option[@value='Tirador']")).click();
		    new Select(driver.findElement(By.name("roles[1]"))).selectByVisibleText("Apoyo");
		    driver.findElement(By.xpath("(//option[@value='Apoyo'])[2]")).click();
		    new Select(driver.findElement(By.name("roles[2]"))).selectByVisibleText("Mago");
		    driver.findElement(By.xpath("(//option[@value='Mago'])[3]")).click();
		    driver.findElement(By.xpath("//button[@type='submit']")).click();	
	}

	public static void goToFormAux(int port,WebDriver driver) throws Exception {		
		driver.get("http://localhost:"+port+"/items");
	    driver.findElement(By.linkText("Add New Item")).click();	
	}
	
	@And("Fill the item creation form with wrong information")
	public void fillFormInCorrect() throws Exception {
		fillFormInCorrectAux(getDriver());
	}
	
	
	public static void fillFormInCorrectAux(WebDriver driver) throws Exception{
		
	    driver.findElement(By.id("title")).click();
	    driver.findElement(By.id("title")).clear();
	    driver.findElement(By.id("title")).sendKeys("");
	    driver.findElement(By.id("description")).click();
	    driver.findElement(By.id("description")).clear();
	    driver.findElement(By.id("description")).sendKeys("");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();	
	}
	
	@Then("I am back at the form page")
	public void FormPageAgain() throws Exception {
		assertEquals(getDriver().getCurrentUrl(),"http://localhost:"+port+"/items/save");
		stopDriver();
	}
	
	
	@Then("I am back at the index page")
	public void GoToIndex() throws Exception {		
		assertEquals(getDriver().getCurrentUrl(),"http://localhost:"+port +"/");
		stopDriver();
	}
	
	@Then("a new Item is created")
	public void itemCreated() throws Exception {
		assertEquals(getDriver().getCurrentUrl(),"http://localhost:"+port+"/items/");
		stopDriver();
	}
	
}