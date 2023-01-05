package ouauth;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GmailApiOauth {
	RequestSpecification requestSpecification;
	ResponseSpecification responseSpecification;
	private static String access_token = "ya29.a0AX9GBdWM9SB1vfEM0KwFzQupDc5GTtHi_Ozdib9BTL60qmCTrEnZibaIOJRX818q-AtFXj1Rmb__x5Wq3HaV6_VoK2Azeci1nd6JkwZ1T6di-DeHwvhQUUw46nCfoQRtnB_ZEYWKOzX9tx8dMBSTgUY7vyAsH2ncaCgYKAc4SAQASFQHUCsbCXZHBoEgkDMoHRD0QKsdAEQ0167";

	@BeforeClass
	public void beforeClass() {
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().setBaseUri("https://gmail.googleapis.com")
				.addHeader("Authorization", "Bearer " + access_token).setContentType(ContentType.JSON)
				.log(LogDetail.ALL);

		requestSpecification = requestSpecBuilder.build();

		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().expectStatusCode(200 | 204)
				.expectContentType(ContentType.JSON).log(LogDetail.ALL);

		responseSpecification = responseSpecBuilder.build();
	}

	@Test
	public void testGetUserProfile() {
		given(requestSpecification).basePath("/gmail/v1/").pathParam("userid", "trainingrestA@gmail.com").when()
				.get("/users/{userid}/profile").then().spec(responseSpecification);
	}

	@Test
	public void testSendEmailMessage() {
		String message = "from: trainingrestA@gmail.com\n" + "to: trainingrestA@gmail.com\n"
				+ "subject: Rest Assured Test Email\n" + "\n" + "Sending from rest assured";

		String urlEncodedMsg = Base64.getUrlEncoder().encodeToString(message.getBytes());
		
		HashMap<String, String> payload = new HashMap<>();
		
		payload.put("raw", urlEncodedMsg);

		given(requestSpecification)
				.basePath("/gmail/v1/")
				.pathParam("userid", "trainingrestA@gmail.com")
				.body(payload)
		.when()
				.post("/users/{userid}/messages/send")
		.then()
				.spec(responseSpecification);
	}
	
	@Test
	public void listEmailMessages() {
		given(requestSpecification)
				.basePath("/gmail/v1/")
				.pathParam("userid", "trainingrestA@gmail.com")
		.when()
			.get("/users/{userid}/messages")
		.then().spec(responseSpecification);
	}
	
	@Test
	public void testGetEmailById() {
		given(requestSpecification)
				.basePath("/gmail/v1/")
				.pathParam("userid", "trainingrestA@gmail.com")
				.pathParam("id","1858050fa7216493")
		.when()
			.get("/users/{userid}/messages/{id}")
		.then().spec(responseSpecification);
	}
	
	
	@Test
	public void testCreateGmailLabel() {
		Map<String, String> payload = new HashMap<>();
		payload.put("name", "Rest Assured Test Label");
		payload.put("messageListVisibility", "show");
		payload.put("labelListVisibility", "labelShow");
		
		given(requestSpecification)
			.basePath("/gmail/v1/")
			.pathParam("userid", "trainingrestA@gmail.com")
			.body(payload)
		.when()
			.post("/users/{userid}/labels")
		.then().spec(responseSpecification);
	}
	
	@Test
	public void testModifyEmail() {
		Map<String, List<String>> payload = new HashMap<>();
		payload.put("addLabelIds", List.of("Label_2"));
		
		given(requestSpecification)
			.basePath("/gmail/v1/")
			.pathParam("userid", "trainingrestA@gmail.com")
			.pathParam("id","1858048f0609b7b4")
			.body(payload)
		.when()
			.post("/users/{userid}/messages/{id}/modify")
		.then().spec(responseSpecification);
	}
	
	@Test
	public void testDeleteEmail() {
		given(requestSpecification)
			.basePath("/gmail/v1/")
			.pathParam("userid", "trainingrestA@gmail.com")
			.pathParam("id","18580101719d8073")
		.when()
			.delete("/users/{userid}/messages/{id}")
		.then().spec(responseSpecification);
	}

}
