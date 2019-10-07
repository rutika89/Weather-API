package stepdefinitions;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import testapiproject.objects.Station;
import testapiproject.utils.LoadProperties;

public class WeatherAPIStepDefinition {

	Properties config = LoadProperties.getProperties();
	RequestSpecification httpRequest;
	Response response;
	
	@Given("^I try to register a station without an API key$")
	public void registerStationWithoutKey() {
		RestAssured.baseURI = config.getProperty("uri");
		httpRequest = RestAssured.given();
		response = httpRequest.post("");
		 
	}
	
	@Then("^I get error message in the response as \"([^\"]*)\"$")
	public void verifyInvalidKeyErrorMsg(String msg) {
		JsonPath jsonResponse = response.jsonPath();
		Assert.assertEquals(msg, jsonResponse.get("message"));
	}
	
	@Then("^status code is \"([^\"]*)\"$")
	public void verifyStatusCode(String statusCode) {
		Assert.assertEquals(Integer.parseInt(statusCode), response.statusCode());
	}
	
	@Given("^I try to delete a station which is already deleted$")
	public void deleteNonExistentStation() {
		RestAssured.baseURI = config.getProperty("uri");
		String apiKey = config.getProperty("appid");
		String deletedStation = config.getProperty("deletedStation");
		httpRequest = RestAssured.given();
		response = httpRequest.delete("/"+deletedStation+"?appid="+apiKey);
		 
	}
	
	@Given("^I register below stations the code is 201$")
	public void registerStation(DataTable data) {
		RestAssured.baseURI = config.getProperty("uri");
		String apiKey = config.getProperty("appid");
		
		List<Station> stationList = data.asList(Station.class);
		ObjectMapper obj = new ObjectMapper();
		  for (Station station : stationList) {
	        try { 
	            // get station object as a json string 
	            String jsonStr = obj.writeValueAsString(station);       
	    		httpRequest = RestAssured.given().header("Content-Type", "application/json").body(jsonStr);
	    		response = httpRequest.post("?appid="+apiKey);
	    		System.out.println("Response is : "+response.asString());
	    		verifyStatusCode("201");
	    	} 
	        catch (IOException e) { 
	            e.printStackTrace(); 
	        } 
		  }		 
	}
}
