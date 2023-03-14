package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;


public class StepDefsForIsBusinessDay {

    public static final String BASE_URL = "/api/v1/isBusinessDay?date=%s&country=%s";
    public static final String SUCCESS = "success";
    private static Response response;


    @When("user calls isBusinessDay with date {string} with country {string}")
    public void userCallsIsBusinessDayWithDateWithCountryGB(String date, String country) {
        RestAssured.port = 3000;
        var request = RestAssured.given();
        String url = String.format(BASE_URL, date, country);
        response = request.get(url);
    }

    @Then("user get result as {string} with with business indicator as {string}")
    public void userGetResultTrue(String expectedStatusStr, String expectedResultStr) {
        var jsonString = response.asString();
        Boolean expectedResult = Boolean.valueOf(expectedResultStr);
        Boolean expectedStatus = SUCCESS.equals(expectedStatusStr);

        Boolean result = JsonPath.from(jsonString).get("results");
        Boolean status = JsonPath.from(jsonString).get("ok");

        Assert.assertEquals(expectedStatus,status);
        Assert.assertEquals(expectedResult,result);
    }

    @Then("user get result as {string} with with error message {string}")
    public void userGetResultAsFailedWithWithErrorMessageAValidDateIsRequired(String expectedStatusStr, String expectedErrorMessage) {
        var jsonString = response.asString();
        Boolean expectedStatus = SUCCESS.equals(expectedStatusStr);

        String errorMessage = JsonPath.from(jsonString).get("errorMessage");
        Boolean status = JsonPath.from(jsonString).get("ok");

        Assert.assertEquals(expectedStatus,status);
        Assert.assertEquals(expectedErrorMessage,errorMessage);
    }


}
