package cucumber.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Utils { 
	
	public static RequestSpecification request;
	
	public RequestSpecification buildRequestSpecifications() throws IOException {
		
		if (request == null) {
			PrintStream log = new PrintStream(new FileOutputStream("log/logging.txt"));
			request = new RequestSpecBuilder()
									.setBaseUri(getBaseUrlPropertyValue("baseUrl"))
									.addQueryParam("key", "qaclick123") 
									.addFilter(RequestLoggingFilter.logRequestTo(log))
									.addFilter(ResponseLoggingFilter.logResponseTo(log))
									.setContentType(ContentType.JSON).build(); 
			return request;
		}
		
		return request;
	}
	
	public ResponseSpecification buildResponseSpecifications() {
		return	new ResponseSpecBuilder()
			.expectStatusCode(200)
			.expectContentType(ContentType.JSON)
			.log(LogDetail.ALL).build();
		}
	
	public String getBaseUrlPropertyValue(String key) throws IOException {
		Properties property = new Properties();
		FileInputStream file;
		file = new FileInputStream("src/test/resources/global.properties");
		property.load(file);
			
		property.getProperty(key);
		return  property.getProperty(key);
	}
	
	public String getJsonPath(Response response, String key) {
		JsonPath jsResponse = new JsonPath(response.asString());
		return jsResponse.getString(key);
	}
}
