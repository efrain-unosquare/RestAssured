package com.example.mytest;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PostRequestTest {
	
	@BeforeTest
	public void setUp() {
		RestAssured.baseURI = "https://reqres.in";
	}
	
	@Test
	public void testPostLogin() {
		RequestSpecification request = given().log().all().header("content-type", "application/json").body(loadJsonBody("Login.json"));
		Response response = request.post("/api/login");
		
		response.then().assertThat().statusCode(200)
						.assertThat().body("token", equalTo("QpwL5tke4Pnpja7X4"));
	}
	
	@Test
	public void testPostRegister() {
		RequestSpecification request = given().log().all().header("content-type", "application/json")
											  .body(loadJsonBody("Register.json"));
		
		Response response = request.post("/api/register");
		
		response.then().assertThat().statusCode(200)
					   .assertThat().body("id", equalTo(4))
					   .assertThat().body("token", equalTo("QpwL5tke4Pnpja7X4"))
					   .log().body();
	}
	
	public String loadJsonBody(String fileName) {
		JSONObject ob = null;
		JSONParser json = new JSONParser();
		
		try (FileReader fileReader = new FileReader("./files/" + fileName)) {
			ob = (JSONObject) json.parse(fileReader);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		return ob.toJSONString();
	}

}
