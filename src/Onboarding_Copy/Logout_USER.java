package Onboarding_Copy;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import Files.PayLoadData;
import Files.ResourcesPortalLogin;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Logout_USER {

	String token=ResourcesPortalLogin.portalLogin();
	String invalid_token=PayLoadData.getProfileIncorrectData();
	//String invalid_user_token=""
	@Test(priority=1,groups="logoutProfile")
	public void logout_user()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		given().
		headers("Content-Type","application/json").headers("Authorization","token "+token).
		when().post("/api/v2/logout/").
		then().assertThat().statusCode(204);
		
	}
	@Test(priority=2,groups="logoutProfile",dependsOnMethods="logout_user")
	public void already_logout_user()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		given().
		headers("Content-Type","application/json").headers("Authorization","token "+token).
		when().post("/api/v2/logout/").
		then().assertThat().statusCode(401).and().body("detail", equalTo("Invalid token."));
	}

	@Test(priority=3,groups="logoutProfile")
	public void logoutInvalidToken()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		
		given().
		headers("Content-Type","application/json").headers("Authorization","token "+invalid_token).
		when().get("/api/v2/logout/").
		then().assertThat().statusCode(401).and().body("detail", equalTo("Invalid token."));
	}
}
