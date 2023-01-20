package cucumber.steps;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.IOException;

import cucumber.databuilder.TestDataBuilder;
import cucumber.enums.Routes;
import cucumber.utils.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddPlaceStep extends Utils{
	
	RequestSpecification requestSpec;
	Response response;
	TestDataBuilder data;
	JsonPath jsResponse;
	public static String placeId;
	
	@Given("Add Place payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
	    // create the request that it'll be used and initialize the request and response specification
		data = new TestDataBuilder();
		
		requestSpec = given()
							.spec(buildRequestSpecifications())
							.body(data.createAddPlaceRequest(name, language, address));
	}

	@When("User calls {string} with {string} http request")
	public void user_calls_with_http_request(String action, String httpMethod) {
		// perform the HTTP call to the ENDPOINT to add a place
		
		if (httpMethod.equalsIgnoreCase("POST"))
			response = requestSpec.when().post(Routes.valueOf(action).getPath());
		else if (httpMethod.equalsIgnoreCase("GET")) 
			response = requestSpec.when().get(Routes.valueOf(action).getPath()); 
	}

	@Then("The API call is success with status codee {int}")
	public void the_api_call_is_success_with_tatust_codee(Integer statusCode) {
		// perform a validation of the status code
		assertEquals(response.getStatusCode(), statusCode);
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String propertyName, String expectedValue) {
		// perform a validation of the expected value in the response body
		
	    assertEquals(getJsonPath(response, propertyName), expectedValue);
	}
	
	@Then("verify place id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using_get_place_api(String expName, String action) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		
		placeId = getJsonPath(response, "place_id");
		
		requestSpec = given()
							.spec(buildRequestSpecifications())
							.queryParam("place_id", placeId);
		user_calls_with_http_request(action, "GET");
		
		String actName = getJsonPath(response, "name");
		assertEquals(actName, expName);
	}
	
	@Given("Delete place payload")
	public void delete_place_payload() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		data = new TestDataBuilder();
		requestSpec = given()
				.spec(buildRequestSpecifications())
				.body(data.deletePayload(placeId));
	}
}
