package com.example.utils;

import io.restassured.path.json.JsonPath;

public class ResponseParserUtil {
	public static JsonPath responseToJson(String response) {
			return new JsonPath(response);
	}
	
}
