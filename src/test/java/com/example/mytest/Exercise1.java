package com.example.mytest;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.example.files.FileData;

import io.restassured.path.json.JsonPath;

public class Exercise1 {
	
	JsonPath mockResponse;
	
	@BeforeTest
	public void setUp() {
		mockResponse = new JsonPath(FileData.coursePrice());
	}
	
	@Test
	public void testNumbOfCourse() {
	int coursesSize =mockResponse.getInt("courses.size()");
	System.out.println(coursesSize);
	}
	
	@Test
	public void testPurchaseAmount() {
		int purchaseAmount = mockResponse.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);
	}
	
	@Test
	public void testTitleCourse() {
		String  titleFirstCourse = mockResponse.get("courses[0].title");
		System.out.println(titleFirstCourse);
	}
	
	@Test
	public void testTitlesAndPrice() {
		int coursesSize =mockResponse.getInt("courses.size()");
		for (int i=0; i < coursesSize; i++) {
			String titles = mockResponse.get("courses["+i+"].title");
			Integer prices = mockResponse.get("courses["+i+"].price");
			
			System.out.println(titles);
			System.out.println(prices);
		}
	}
	
	@Test
	public void testNumbOfCopies() {
		int coursesSize =mockResponse.getInt("courses.size()");
		for (int i=0; i < coursesSize; i++) {
			String titles = mockResponse.get("courses["+i+"].title");
			
			if(titles.equalsIgnoreCase("RPA")) {
			Integer prices = mockResponse.get("courses["+i+"].price");
			System.out.println(prices);
			break;
			}
		}
	}
	
	@Test
	public void testSumCoursesMatchPurchaseAmount() {
		Integer price = 0;
		Integer copies = 0;
		Integer amount = 0;
		int purchaseAmount = mockResponse.getInt("dashboard.purchaseAmount");
		int coursesSize =mockResponse.getInt("courses.size()");
		
		for(int i= 0; i < coursesSize; i++) {
			price =	mockResponse.get("courses["+i+"].price");
			copies = mockResponse.get("courses["+i+"].copies");
			amount += price * copies;
		}
		assertEquals(purchaseAmount, amount);
	}

}
