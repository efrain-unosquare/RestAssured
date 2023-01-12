package ResponsePayload;

import java.util.List;

public class CourseResponse {
	private String url;
	private String services;
	private String expertise;
	private Courses courses;
	private String instructor;
	private String linkedIn;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public Courses getCourses() {
		return courses;
	}

	public void setCourses(Courses courses) {
		this.courses = courses;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public String getLinkedIn() {
		return linkedIn;
	}

	public void setLinkedin(String linkedIn) {
		this.linkedIn = linkedIn;
	}
	
	public static class Courses {
		
		private List<WebAutomation> WebAutomation;
		private List<API> api;
		private List<Mobile> mobile;

		public List<WebAutomation> getWebAutomation() {
			return WebAutomation;
		}


		public void setWebAutomation(List<WebAutomation> webAutomation) {
			WebAutomation = webAutomation;
		}


		public List<API> getApi() {
			return api;
		}


		public void setApi(List<API> api) {
			this.api = api;
		}


		public List<Mobile> getMobile() {
			return mobile;
		}


		public void setMobile(List<Mobile> mobile) {
			this.mobile = mobile;
		}


		public static class WebAutomation{
			private String courseTitle;
			private String price;
			public String getCourseTitle() {
				return courseTitle;
			}
			public void setCourseTitle(String courseTitle) {
				this.courseTitle = courseTitle;
			}
			public String getPrice() {
				return price;
			}
			public void setPrice(String price) {
				this.price = price;
			}
			
			
		}
		

		public static class API{
			private String courseTitle;
			private String price;
			
			public String getCourseTitle() {
				return courseTitle;
			}
			public void setCourseTitle(String courseTitle) {
				this.courseTitle = courseTitle;
			}
			public String getPrice() {
				return price;
			}
			public void setPrice(String price) {
				this.price = price;
			}
			
			
		}
		

		public static class Mobile{
			private String courseTitle;
			private String price;
			public String getCourseTitle() {
				return courseTitle;
			}
			public void setCourseTitle(String courseTitle) {
				this.courseTitle = courseTitle;
			}
			public String getPrice() {
				return price;
			}
			public void setPrice(String price) {
				this.price = price;
			}
			
			
		}
		
	}

}
