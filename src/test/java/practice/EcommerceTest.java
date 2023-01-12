package practice;

import org.testng.Assert;
import org.testng.annotations.Test;

import RequestPayload.CreateOrderRequest;
import RequestPayload.CreateOrderRequest.Orders;
import RequestPayload.LoginRequest;
import ResponsePayload.CreateOrderResponse;
import ResponsePayload.CreateProductResponse;
import ResponsePayload.LoginResponse;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.List;

public class EcommerceTest {
	
	RequestSpecification requestSpec;
	ResponseSpecification responseSpec;
	
	// TODO add your email
	private static String email = "";
	//TODO add your password
	private static String password = "";
	private static String token = "";
	private static String userId = "";
	private static String productId = "";
	private static String orderId = "";
	
	@Test(priority = 1)
	public void testLogin() {
		LoginRequest loginInf = new LoginRequest();
		
		loginInf.setUserEmail(email);
		loginInf.setUserPassword(password);
		
		requestSpec = setContentTypeRequestSpec("JSON");
		responseSpec = setResponseSpecification(200);
		
		LoginResponse logRes = given(requestSpec)
											.log()
											.all()
											.body(loginInf)
							.when()
								.post("/api/ecom/auth/login")
							.then()
								.spec(responseSpec)
								.extract()
								.response()
								.as(LoginResponse.class);
		
		token = logRes.getToken();
		userId = logRes.getUserId();
		System.out.println("Token -----> " + logRes.getToken());
		System.out.println("User Id -----> " + logRes.getUserId());
		
	}
	
	@Test(priority = 2)
	public void testCreateProduct() {
		
		requestSpec = setContentTypeRequestSpec("Multipart");
		responseSpec = setResponseSpecification(201);
		
		CreateProductResponse res =	given(requestSpec)
													.header("authorization",token)
													.param("productName", "Laptop HP")
													.param("productAddedBy", userId)
													.param("productCategory", "fashion")
													.param("productSubCategory", "shirts")
													.param("productPrice", "16000")
													.param("productDescription", "Hewelt Packart Laptop")
													.param("productFor", "men")
													.multiPart("productImage", new File("/Users/efrain.martinez/Downloads/laptop.jpg"))
									.when()
										.post("/api/ecom/product/add-product")
									.then()
										.spec(responseSpec)
										.extract()
										.response()
										.as(CreateProductResponse.class);
		productId = res.getProductId();
		System.out.println("Product id ------> " + res.getProductId());
	}
	
	@Test(priority = 3)
	public void testCreateOrder() {
		
		CreateOrderRequest orderRequest = new CreateOrderRequest();
		Orders orders = new Orders();
		
		orders.setCountry("Mexico");
		orders.setProductOrderedId(productId);
		
		orderRequest.setOrders(List.of(orders));
		
		requestSpec = setContentTypeRequestSpec("JSON");
		responseSpec = setResponseSpecification(201);
		
		CreateOrderResponse res = given(requestSpec)
										.header("authorization",token)
										.body(orderRequest)
									.when()
										.post("/api/ecom/order/create-order")
									.then()
										.spec(responseSpec)
										.extract()
										.response()
										.as(CreateOrderResponse.class);
		
		orderId = res.getOrders().get(0);
		System.out.println("Order -------> " + res.getMessage());
		
	}
	
	@Test(priority = 4)
	public void testDeleteProduct() {
		requestSpec = setContentTypeRequestSpec("JSON");
		responseSpec = setResponseSpecification(200);
		
		String res = given(requestSpec)
						  .header("authorization",token)
						  .pathParam("id", productId)
					 .when()
						  .delete("/api/ecom/product/delete-product/{id}")
					 .then()
						  .spec(responseSpec)
						  .extract()
						  .response()
						  .asString();
		
		JsonPath jsonRes = new JsonPath(res);
		
		System.out.println("response -------> " + jsonRes.getString("message"));
		
		Assert.assertEquals("Product Deleted Successfully", jsonRes.getString("message"));
		
	}
	
	@Test(priority = 5)
	public void testDeleteOrder() {
		requestSpec = setContentTypeRequestSpec("JSON");
		responseSpec = setResponseSpecification(200);
		
		String res = given(requestSpec)
							.header("authorization", token)
							.pathParam("id", orderId)
					 .when()
							.delete("/api/ecom/order/delete-order/{id}")
					 .then()
							.spec(responseSpec)
							.extract()
							.response()
							.asString();
		
		JsonPath jsonResponse = new JsonPath(res);
		
		Assert.assertEquals("Orders Deleted Successfully", jsonResponse.getString("message"));
	}
	
	public RequestSpecification setContentTypeRequestSpec(String contentType) {
		if (contentType == "Multipart") {
			return new RequestSpecBuilder()
					.setBaseUri("https://rahulshettyacademy.com")
					.setContentType(ContentType.MULTIPART).build();
		}
		return new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
	}
	
	public ResponseSpecification setResponseSpecification(Integer statusCode) {
	return	new ResponseSpecBuilder()
		.expectStatusCode(statusCode)
		.expectContentType(ContentType.JSON)
		.log(LogDetail.ALL).build();
	}

}
