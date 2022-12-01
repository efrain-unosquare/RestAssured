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

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CrudOperationsTest {

	@BeforeTest
	public void setUp() {
		RestAssured.baseURI = "https://reqres.in";
	}

	@Test
	public void testAddUser() {
		given().header("content-type", "application/json").log().all().body(createJsonObject("POST").toString()).when()
				.post("/api/users").then().log().all().assertThat().statusCode(201).body("name", equalTo("Efrain"));
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

	@Test

	public void testGetSingleUseActivity() {

		given().log().all().pathParam("id", "2").when().get("/api/users/{id}").then().log().all().assertThat()
				.statusCode(200).assertThat().contentType(ContentType.JSON).assertThat().body("data.id", equalTo(2))
				.assertThat().body("data.email", equalTo("janet.weaver@reqres.in")).assertThat()
				.body("data.first_name", equalTo("Janet")).assertThat().body("data.last_name", equalTo("Weaver"))
				.assertThat().body("data.avatar", equalTo("https://reqres.in/img/faces/2-image.jpg")).assertThat()
				.body("support.url", equalTo("https://reqres.in/#support-heading")).assertThat().body("support.text",
						equalTo("To keep ReqRes free, contributions towards server costs are appreciated!"));

		Reporter.log("Success 200 status code ");

	}

	@Test
	public void testGetSingleResourceActivity() {

		RequestSpecification request = RestAssured.given().log().all().pathParam("id", "2");
		Response response = request.get("/api/unknown/{id}");

		response.then().log().all().assertThat().statusCode(200).assertThat().body("data.id", equalTo(2)).assertThat()
				.body("data.name", equalTo("fuchsia rose")).assertThat().body("data.year", equalTo(2001)).assertThat()
				.body("data.color", equalTo("#C74375")).assertThat().body("data.pantone_value", equalTo("17-2031"))
				.assertThat().body("support.url", equalTo("https://reqres.in/#support-heading")).assertThat()
				.body("support.text",
						equalTo("To keep ReqRes free, contributions towards server costs are appreciated!"));

		System.out.println("Register json body " + loadJsonFile().toJSONString());

		Reporter.log("Success 200 status code ");

	}

	public JSONObject loadJsonFile() {
		JSONObject ob = null;
		JSONParser json = new JSONParser();

		try (FileReader fileReader = new FileReader("./files/Register.json")) {
			ob = (JSONObject) json.parse(fileReader);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		return ob;
	}

	public JSONObject createJsonObject(String httpMethod) {
		JSONObject requestBody = new JSONObject();
		switch (httpMethod) {
		case "POST":
			requestBody.put("name", "Efrain");
			requestBody.put("job", "trainee");
			break;
		case "PUT":
			requestBody.put("name", "Efrain");
			requestBody.put("job", "zion resident");
			break;
		default:
			break;
		}

		return requestBody;
	}
}
