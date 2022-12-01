package com.example.mytest;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.example.files.FileData;
import com.example.utils.ResponseParserUtil;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;

public class DynamicJson {
	
	
	@BeforeTest
	public void setUp() {
		RestAssured.baseURI="http://216.10.245.166";
	}
	
	@DataProvider(name = "BooksData")
	public Object[][] getData() {
		
		return new Object[][] { {"bcd", "4321"}, {"bcd","7819"}, {"bcd","6543"} };
	}
	
	
	@Test(dataProvider = "BooksData")
	public void addBook(String isbn, String aisle) {
		String response = given().header("content-type", "application/json").body(FileData.addBook(isbn, aisle))
		.when().post("/Library/Addbook.php")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		String actResult = ResponseParserUtil.responseToJson(response).getString("ID");
		String expResul =isbn+ResponseParserUtil.responseToJson(FileData.addBook(isbn, aisle)).getString("aisle");
		assertEquals(actResult, expResul);
		
		//System.out.println(resResult);
		
	}

}
