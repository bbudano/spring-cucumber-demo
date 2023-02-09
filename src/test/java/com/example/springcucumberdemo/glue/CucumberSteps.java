package com.example.springcucumberdemo.glue;

import com.example.springcucumberdemo.model.Guitar;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class CucumberSteps {

    @LocalServerPort
    String port;

    private HttpEntity<Guitar> requestEntity;

    private ResponseEntity<Guitar> response;

    private final RestTemplate restTemplate = new RestTemplate();

    @Given("the client wants to create a guitar with manufacturer {string} and model {string}" +
            " and finish {string} and serial number {string} and amount {bigdecimal}")
    public void givenClientWantsToCreateGuitar(String manufacturer, String model, String finish, String serialNumber, BigDecimal amount) {
        var guitar = new Guitar(null, manufacturer, model, finish, serialNumber, amount);
        requestEntity = new HttpEntity<>(guitar);
    }

    @When("the client calls endpoint {string}")
    public void whenClientCallsEndpoint(String path) {
        this.response = restTemplate.exchange(
                "http://localhost:" + this.port + path,
                HttpMethod.POST,
                requestEntity,
                Guitar.class);
    }

    @Then("response status code is {int}")
    public void thenStatusCode(int expectedStatusCode) {
        Assertions.assertEquals(expectedStatusCode, response.getStatusCode().value());
    }

    @And("response body contains manufacturer {string} and model {string} and finish {string} and serial number {string} and amount {bigdecimal}")
    public void responseBodyMatches(String manufacturer, String model, String finish, String serialNumber, BigDecimal amount) {
        Assertions.assertNotNull(response.getBody());
        Assertions.assertNotNull(response.getBody().id());
        Assertions.assertEquals(manufacturer, response.getBody().manufacturer());
        Assertions.assertEquals(model, response.getBody().model());
        Assertions.assertEquals(finish, response.getBody().finish());
        Assertions.assertEquals(serialNumber, response.getBody().serialNumber());
        Assertions.assertEquals(amount, response.getBody().amount());
    }

}
