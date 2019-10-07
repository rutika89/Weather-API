package runner;

import java.io.*;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import testapiproject.utils.LoadProperties;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/apiTests", glue = { "stepdefinitions" }, plugin = { "pretty",
		"json:target/cucumber-reports/Cucumber.json", "junit:target/cucumber-reports/Cucumber.xml",
		"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html" },
		tags = {"@RegisterStation, @APIKeyError, @StationNotFound"})

public class TestRunner {

	@AfterClass
	public static void writeExtentReport() {
		Reporter.loadXMLConfig(new File(LoadProperties.getReportConfigPath()));
	}
}
