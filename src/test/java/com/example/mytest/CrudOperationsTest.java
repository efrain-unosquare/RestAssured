package com.example.mytest;

import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.testng.Assert.assertEquals;

import org.json.JSONObject;

public class CrudOperationsTest {
	
	JSONObject requestBody;
	
	@BeforeTest
	public void setUp() {
		RestAssured.baseURI="https://reqres.in";
		requestBody = new JSONObject();
	}
	
	@Test
	public void testAddUser() {
		given().header("content-type", "application/json").log().all().body(createJsonObject("POST").toString())
		.when().post("/api/users")
		.then().log().all().assertThat().statusCode(201).body("name", equalTo("Efrain"));
	}
	
	@Test
	public void testGetUser() {
		RequestSpecification request = RestAssured.given().log().all();
		Response response = request.get("/api/users");
		
		assertEquals(response.getStatusCode(), 200);
		Reporter.log("Success status code validation");
		
		response.then().body("data.size()", greaterThan(0));
		Reporter.log(response.body().asString());
	}
	
	public JSONObject createJsonObject(String httpMethod) {
		
		switch(httpMethod) {
		case "POST": 
			requestBody.put("name", "Efrain");
			requestBody.put("job", "trainee");
			break;
		case "PUT": 
			requestBody.put("name", "Efrain");
			requestBody.put("job", "zion resident");
			break;
		default: break;
		}
		
		return requestBody;
	}
}
