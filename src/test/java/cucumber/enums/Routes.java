package cucumber.enums;

public enum Routes {
	addPlaceApi("/maps/api/place/add/json"),
	getPlaceApi("/maps/api/place/get/json"),
	deletePlaceApi("/maps/api/place/delete/json");
	
	private String path;

	Routes(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
}
