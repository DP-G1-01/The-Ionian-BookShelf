package org.springframework.samples.the_ionian_bookshelf.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features= {"src/test/java/org/"},	
        tags = {"not @ignore"},
        plugin = {"pretty",                                
                "json:target/cucumber-reports/cucumber-report.json"}, 
        monochrome=true)
public class CucumberUITest {
}