package Serialize;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import RequestPayload.AddPlaceRequest;
import RequestPayload.AddPlaceRequest.Location;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

import java.util.List;

public class SpecBuilderTests {
	RequestSpecification requestSpec;
	ResponseSpecification responseSpec;
	
	@BeforeClass
	public void init() {
		RequestSpecBuilder request = new RequestSpecBuilder()
															.setBaseUri("https://rahulshettyacademy.com")
															.addQueryParam("key", "qaclick123")
															.setContentType(ContentType.JSON);
		
		requestSpec = request.build();
		
		ResponseSpecBuilder response = new ResponseSpecBuilder()
																.expectStatusCode(200)
																.expectContentType(ContentType.JSON)
																.log(LogDetail.ALL);
		
		responseSpec = response.build();
		
	}
	
	@Test
	public void serializeTestPayload() {
		AddPlaceRequest requestBody = new AddPlaceRequest();
		Location location = new Location();
		location.setLat(-38.383494);
		location.setLng(33.427362);
		
		requestBody.setAccuracy(50);
		requestBody.setAddress("29, side layout, cohen 09");
		requestBody.setLanguage("Spanish-ES");
		requestBody.setPhone_number("(+91) 983 893 3937");
		requestBody.setWebsite("https://rahulshettyacademy.com");
		requestBody.setName("Frontline house");
		requestBody.setTypes(List.of("Shoe Park", "Shop", "Grosery Store"));
		requestBody.setLocation(location);
		
		
		requestSpec = given()
							.spec(requestSpec)
							.body(requestBody);
		
		Response res = requestSpec.when()
						.post("/maps/api/place/add/json")
				   .then()
						.spec(responseSpec)
						.extract()
						.response();
				
		System.out.println("Response ----> " + res.asString());
	}

}
