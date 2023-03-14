package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;


public class StepDefsForSettlementDate {

    public static final String BASE_URL = "/api/v1/settlementDate?initialDate=%s&delay=%s&country=%s";
    public static final String SUCCESS = "success";
    private static Response response;

    @When("user calls settlementDate with date {string} and delay {string} and country {string}")
    public void userCallsIsBusinessDayWithDate(String date, String delay, String country) {
        RestAssured.port = 3000;
        var request = RestAssured.given();
        String url = String.format(BASE_URL, date, delay, country);
        response = request.get(url);
    }

    @Then("user get status code {string}")
    public void userGetStatusCode(String statusCodeStr) {
        int statusCode = Integer.parseInt(statusCodeStr);
        Assert.assertEquals(response.getStatusCode(), statusCode);
    }
}
