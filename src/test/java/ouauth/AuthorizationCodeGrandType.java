package ouauth;

import static io.restassured.RestAssured.given;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import ResponsePayload.CourseResponse;
import ResponsePayload.CourseResponse.Courses.API;
import ResponsePayload.CourseResponse.Courses.WebAutomation;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class AuthorizationCodeGrandType {

	private static String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AWgavddnG8F9OJP0GJRuWcoep3Qj6zJt8E94B7AlmyLk2s4r0uLKgeVaq84uLOFusBK2zw&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";

	/**
	 * This method is for hitting the real end point who ask for authentication and
	 * authorization through authorization code
	 */
	@Test
	public void testGetCourses() {
		CourseResponse response = given().queryParam("access_token", getAccessToken()).expect()
				.defaultParser(Parser.JSON).when().get("https://rahulshettyacademy.com/getCourse.php")
				.as(CourseResponse.class);

		 System.out.println("Intructor from response --------> "+response.getInstructor());
		 System.out.println("Course title --------> "+response.getCourses().getApi().get(1).getCourseTitle());
		 
		 List<API> apiCourses = response.getCourses().getApi();
		 List<WebAutomation> webAutoCourses = response.getCourses().getWebAutomation();
		 
		 apiCourses.forEach(course -> {
			 if (course.getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println("Course price -----> " + course.getPrice());
			}
		 });
		 
		 //TODO Activity print all the course title from the web automation
		 String[] expResult = {"Selenium Webdriver Java","Cypress","Protractor"};
		 ArrayList<String> actResult = new ArrayList<>();
		 
		 webAutoCourses.forEach(webAutoCourse -> {
			 System.out.println("Web Automation course titles ------> " + webAutoCourse.getCourseTitle());
			 actResult.add(webAutoCourse.getCourseTitle());
		 });
		 
		 Assert.assertTrue(actResult.equals(Arrays.asList(expResult)));
	}

	/**
	 * this method is used to get the access token using the client id and client
	 * secret
	 * 
	 * @return string access_token
	 */
	public String getAccessToken() {
		String code = url.split("code=")[1].split("&scope")[0];

		System.out.println("URL CODE ------> " + code);
		String accessTokenRes = given().urlEncodingEnabled(false).queryParams("code", code)
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParams("grant_type", "authorization_code").when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();

		return new JsonPath(accessTokenRes).getString("access_token");
	}

	/**
	 * this method is used to run SELENIUM script to get the code from the URL when
	 * we try to use google login form as login method
	 * 
	 * @NOTE this method is current not allowed by google, due google is blocking
	 *       the automation login
	 * @return string code
	 */
	public String getAuthorizationCode() {
		System.setProperty("webdriver.chrome.driver", "webdrivers/chromedriver");
		WebDriver driver = new ChromeDriver();

		driver.get(
				"https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		driver.findElement(By.id("//input[@id='identifierId']")).sendKeys("trainingrestA@gmail.com");
		driver.findElement(By.id("//input[@id='identifierId']")).sendKeys(Keys.ENTER);

		new WebDriverWait(driver, Duration.ofSeconds(3))
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='password']")));

		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("XXX");
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);

		new WebDriverWait(driver, Duration.ofSeconds(3))
				.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

		String urlCode = driver.getCurrentUrl().split("code=")[1].split("&scope")[0];

		return urlCode;

	}
}
