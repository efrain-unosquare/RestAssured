package RequestPayload;

import java.util.List;

public class CreateOrderRequest {
	private List<Orders> orders;
	
	public List<Orders> getOrders() {
		return orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}

	public static class Orders {
		private String country;
		private String productOrderedId;
		
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getProductOrderedId() {
			return productOrderedId;
		}
		public void setProductOrderedId(String productOrderedId) {
			this.productOrderedId = productOrderedId;
		}
		
		
	}
}
