package cucumber.databuilder;

import java.util.List;

import RequestPayload.AddPlaceRequest;
import RequestPayload.AddPlaceRequest.Location;

public class TestDataBuilder {

	public AddPlaceRequest createAddPlaceRequest(String name, String language, String address) {
		AddPlaceRequest requestBody = new AddPlaceRequest();
		Location location = new Location();
		location.setLat(-38.383494);
		location.setLng(33.427362);  
		
		requestBody.setAccuracy(50);
		requestBody.setAddress(address);
		requestBody.setLanguage(language);
		requestBody.setPhone_number("(+91) 983 893 3937");
		requestBody.setWebsite("https://rahulshettyacademy.com");
		requestBody.setName(name);
		requestBody.setTypes(List.of("Shoe Park", "Shop", "Grosery Store"));
		requestBody.setLocation(location);
		
		return requestBody;
	}
	
	public String deletePayload(String placeId) {
		return "{\r\n  \"place_id\":\""+placeId+"\" \r\n}";
	}
}
