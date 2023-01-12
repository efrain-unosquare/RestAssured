package Serialize;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import RequestPayload.AddPlaceRequest;
import RequestPayload.AddPlaceRequest.Location;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.util.List;

public class SerializeTests {
	
	@BeforeClass
	public void init() {
		RestAssured.baseURI="https://rahulshettyacademy.com";
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
		
		
		Response response =	given()
								.log()
								.all()
								.queryParam("key", "qaclick123")
								.body(requestBody)
							.when()
								.post("/maps/api/place/add/json")
							.then()
								.assertThat()
								.statusCode(200)
								.extract()
								.response();
				
		System.out.println("Response ----> " + response.asString());
	}

}
