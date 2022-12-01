package com.example.mytest;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.example.utils.ResponseParserUtil;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MyTestClass {

	@BeforeTest
	public void setUp() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";

	}

	@Test
	public void myFirstAssuredTest() throws IOException {
		
	
		String response = given().log().all().queryParam("key", "qaclick123").header("content-type", "application/json")
				.body(new String(Files.readAllBytes(Paths.get("./files/addPlace.json")))).when().post("/maps/api/place/add/json").then().assertThat()
				.statusCode(200).body("scope", equalTo("APP")).header("server", "Apache/2.4.41 (Ubuntu)").extract()
				.response().asString();

		// add place and update place with new address
		// get place to validate if new address is present in the response
		System.out.println(response);
		String placeId = ResponseParserUtil.responseToJson(response).getString("place_id");

		System.out.println(placeId);

		// update

		String newAddress = "25 las almas, tangamandapio";

		given().log().all().queryParam("key", "qaclick123")
				.body("{\n" + "\"place_id\":\"" + placeId + "\",\n" + "\"address\":\"" + newAddress + "\",\n"
						+ "\"key\":\"qaclick123\"\n" + "}\n" + "")
				.when().put("/maps/api/place/update/json").then().assertThat().log().all().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

		// GET

		String getResponse = given().log().all().queryParam("place_id", placeId).queryParam("key", "qaclick123")
				.header("content-type", "application/json").when().get("/maps/api/place/get/json").then().assertThat()
				.log().all().statusCode(200).extract().response().asString();
		
		String getJsonRes = ResponseParserUtil.responseToJson(getResponse).getString("address");
		
		assertEquals(getJsonRes, newAddress);

	}

}
