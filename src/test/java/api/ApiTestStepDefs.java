package api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.LinkedHashMap;
import java.util.List;

public class ApiTestStepDefs {

    public static final String EMAIL = "gianna@hightable.test";
    public static final String PASSWORD = "thedantonio1";
    private static final String BASE_URL = "https://qa-challenge-api.scratchpay.com/api";
    private static final String AUTH_URL = "/auth?email=%s&password=%s";
    private static final String EMAIL_ADDRESS_LIST_URL = "/clinics/%s/emails";
    private static final String CLINICS_SEARCH_URL = "/clinics?term=%s";
    private static Response response;
    private static String listOfEmailAddresses;
    private static String listOfClinics;
    private static String jwt;


    @Given("User Authorized")
    public void userAuthorized() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        String url = String.format(AUTH_URL, EMAIL, PASSWORD);
        response = request.get(url);

        String jsonString = response.asString();
        LinkedHashMap<String,Object> data = JsonPath.from(jsonString).get("data");
        LinkedHashMap<String,Object> session = (LinkedHashMap<String,Object>)data.get("session");
        jwt = (String)session.get("token");
    }

    @When("The logged in user tries to get a list of email addresses of practice id {int}")
    public void theLoggedInUserTriesToGetAListOfEmailAddressesOfPracticeId(int practiceId) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        String url = String.format(EMAIL_ADDRESS_LIST_URL, practiceId);
        request.header("Authorization", "Bearer " + jwt);
        response = request.get(url);
        listOfEmailAddresses = response.asString();
    }


    @Then("User should be prevented from getting the list")
    public void userShouldBePreventedFromGettingTheList() {
        boolean success = JsonPath.from(listOfEmailAddresses).get("ok");
        LinkedHashMap<String,Object> data = JsonPath.from(listOfEmailAddresses).get("data");
        String message = (String) data.get("message");
        String error = (String) data.get("error");

        Assert.assertFalse(success);
        Assert.assertEquals("Error: User does not have permissions", error);
        Assert.assertEquals("An error happened", message);
    }

    @When("The logged in user tries to get a clinics by term {string}")
    public void theLoggedInUserTriesToGetAClinicsByTermVeterinary(String term) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        String url = String.format(CLINICS_SEARCH_URL, term);
        request.header("Authorization", "Bearer " + jwt);
        response = request.get(url);
        listOfClinics = response.asString();
    }

    @Then("User able to get clinics")
    public void userAbleToGetClinics() {
        boolean success = JsonPath.from(listOfClinics).get("ok");
        List<Object> data = JsonPath.from(listOfClinics).get("data");

        Assert.assertTrue(success);
        Assert.assertNotNull(data);
    }

    @When("Not logged in user tries to get a clinics by term {string}")
    public void notLoggedInUserTriesToGetAClinicsByTermVeterinary(String term) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        String url = String.format(CLINICS_SEARCH_URL, term);
        response = request.get(url);
        listOfClinics = response.asString();
    }

    @Then("User should be prevented from getting the clinics")
    public void userShouldBePreventedFromGettingTheClinics() {
        boolean success = JsonPath.from(listOfClinics).get("ok");
        LinkedHashMap<String,Object> data = JsonPath.from(listOfClinics).get("data");
        String message = (String) data.get("message");

        Assert.assertFalse(success);
        Assert.assertEquals("You need to be authorized for this action.", message);
    }


}
