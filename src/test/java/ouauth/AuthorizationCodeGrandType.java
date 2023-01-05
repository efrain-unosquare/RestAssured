package ouauth;

import static io.restassured.RestAssured.given;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class AuthorizationCodeGrandType {
	
	private static String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AWgavdcO1bDvaJRx29_iPR0tEjKcQCJS0pQtmc0Ax811pExLCncvKhvona8pP10o1ySaiA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent";

	
	/** 
	 * This method is for hitting the real end point who ask for authentication and authorization 
	 * through authorization code
	 */
	@Test
	public void testGetCourses() {
		String response = given().queryParam("access_token", getAccessToken()).when()
				.get("https://rahulshettyacademy.com/getCourse.php").asString();

		System.out.println(response);
	}

	/**
	 * this method is used to get the access token using the client id and client secret
	 * @return string access_token
	 */
	public String getAccessToken() {
		String code = url.split("code=")[1].split("&scope")[0];
		
		System.out.println("URL CODE ------> "+ code);
		String accessTokenRes = given().urlEncodingEnabled(false).queryParams("code", code)
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParams("grant_type", "authorization_code").when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		return new JsonPath(accessTokenRes).getString("access_token");
	}
	
	/**
	 * this method is used to run SELENIUM script to get the code from the URL when we try to use google login form 
	 * as login method
	 * 
	 * @NOTE this method is current not allowed by google, due google is blocking the automation login
	 * @return string code
	 */
	public String getAuthorizationCode() {
		System.setProperty("webdriver.chrome.driver", "webdrivers/chromedriver");
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		driver.findElement(By.id("//input[@id='identifierId']")).sendKeys("trainingrestA@gmail.com");
		driver.findElement(By.id("//input[@id='identifierId']")).sendKeys(Keys.ENTER);
		
		new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='password']")));
		
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("XXX");
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
		
		
		new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
		
		String urlCode = driver.getCurrentUrl().split("code=")[1].split("&scope")[0];
		
		
		return urlCode;
		
	}
}
