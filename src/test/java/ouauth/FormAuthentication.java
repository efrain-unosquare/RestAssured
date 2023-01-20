package ouauth;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import java.util.Map;

public class FormAuthentication {
	
	SessionFilter filter;

	@BeforeClass
	public void beforeClass() {
		
		RestAssured.requestSpecification = new RequestSpecBuilder()
														.setRelaxedHTTPSValidation() // this line is to avoid PKIX Path building failed 
																					 //due a ssl certificate validation 
																					 //due the ssl certificate is not valid for https
														.setBaseUri("https://localhost:8443")
														.build();
		
		/*
		 * uncomment the below line if the application is using a custom session id 
		 * please refer to this link for reference https://github.com/rest-assured/rest-assured/wiki/Usage#session-support
		 */
		//RestAssured.config = RestAssured.config().sessionConfig(new SessionConfig().sessionIdName("your_custom_session_id_name"));
	}

	@Test
	public void testFormAuthUsingCsrfToken() {
		filter = new SessionFilter();
		given()
			.csrf("/login")
			.auth().form("dan", "dan123", new FormAuthConfig("/signin", "txtUsername", "txtPassword"))
			.filter(filter)
			.log().all()
		.when()
			.get("/login")
		.then()
			.log().all()
			.assertThat()
			.statusCode(200);
		
		System.out.println("Session Id -------> " + filter.getSessionId());
		
		given()
			  .sessionId(filter.getSessionId())
			  .log().all().
	    when()
	    	  .get("/profile/index").
	    then()
	    	  .log().all()
	    	  .assertThat()
	    	  .statusCode(200)
	    	  .body("html.body.div.p", equalTo("This is User Profile\\Index. Only authenticated people can see this"));
		
	}

	@Test
	public void testFormAuthUsingCookie() {
		filter = new SessionFilter();
		given()
		.csrf("/login")
		.auth().form("dan", "dan123", new FormAuthConfig("/signin", "txtUsername", "txtPassword"))
		.filter(filter)
		.log().all()
	.when()
		.get("/login")
	.then()
		.log().all()
		.assertThat()
		.statusCode(200);
	
	System.out.println("Cookie -------> " + filter.getSessionId());
	
	Cookie cookie = new Cookie.Builder("JSESSIONID",filter.getSessionId())
											.setSecured(true)
											.setHttpOnly(true)
											.setComment("My cookie")
											.build();
	
	Cookie cookie1 = new Cookie.Builder("Second cookie","I like eat cookies").build();
	
	given()
		  //.cookie(cookie)
		  .cookies(new Cookies(cookie, cookie1))
		  .log().all().
    when()
    	  .get("/profile/index").
    then()
    	  .log().all()
    	  .assertThat()
    	  .statusCode(200)
    	  .body("html.body.div.p", equalTo("This is User Profile\\Index. Only authenticated people can see this"));
	}
	
	
	@Test
	public void testFetchCookies() {
		
		// fetch single cookie
		Response response=	given()
							  	  .log().all().
							when()
					  	  		  .get("/profile/index").
					  	  	then()
							  	  .log().all()
							  	  .assertThat()
							  	  .statusCode(200)
							  	  .extract()
							  	  .response();
		
		System.out.println("Getting the cookie ------> " + response.getCookie("JSESSIONID"));
		System.out.println("Detail cookie ------> " + response.getDetailedCookie("JSESSIONID"));
		
	}
	
	@Test
	public void testFetchMultipleCookies() {
		
		Response response=	given()
							  	  .log().all().
							when()
					  	  		  .get("/profile/index").
					  	  	then()
							  	  .log().all()
							  	  .assertThat()
							  	  .statusCode(200)
							  	  .extract()
							  	  .response();
		
		Map<String, String> cookies = response.getCookies();
		
		for (Map.Entry<String, String> entry : cookies.entrySet()) {
			System.out.println("Cookie Name = " + entry.getKey());
			System.out.println("Cookie Value = " + entry.getValue());
			
		}
		
		Cookies cookieDetails = response.getDetailedCookies();
		
		List<Cookie> cookieList = cookieDetails.asList();
		for (Cookie cookie : cookieList) {
			System.out.println("cookie = " + cookie.toString());
		}
	}

}
