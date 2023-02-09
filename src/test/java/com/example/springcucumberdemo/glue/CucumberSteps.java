package com.example.springcucumberdemo.glue;

import com.example.springcucumberdemo.model.Guitar;
import com.example.springcucumberdemo.repository.GuitarRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CucumberSteps {

    @Autowired
    private GuitarRepository guitarRepository;

    @LocalServerPort
    String port;

    private HttpEntity<Guitar> requestEntity;

    private ResponseEntity<Guitar> guitarResponse;

    private ResponseEntity<List<Guitar>> guitarsResponse;

    private final TestRestTemplate restTemplate;

    @After
    public void tearDown() {
        guitarRepository.deleteAll();
    }

    @Given("the client wants to create a guitar with manufacturer {string} and model {string}" +
            " and finish {string} and serial number {string} and amount {bigdecimal}")
    public void givenClientWantsToCreateGuitar(String manufacturer, String model, String finish, String serialNumber, BigDecimal amount) {
        var guitar = new Guitar(null, manufacturer, model, finish, serialNumber, amount);
        requestEntity = new HttpEntity<>(guitar);
    }

    @When("the client makes POST request on endpoint {string}")
    public void whenClientCallsEndpoint(String path) {
        guitarResponse = restTemplate.exchange(
                "http://localhost:" + this.port + path,
                HttpMethod.POST,
                requestEntity,
                Guitar.class);
    }

    @Then("response status code is {int}")
    public void thenStatusCode(int expectedStatusCode) {
        Assertions.assertEquals(expectedStatusCode, guitarResponse.getStatusCode().value());
    }

    @And("response body contains manufacturer {string} and model {string} and finish {string} and serial number {string} and amount {bigdecimal}")
    public void responseBodyMatches(String manufacturer, String model, String finish, String serialNumber, BigDecimal amount) {
        Assertions.assertNotNull(guitarResponse.getBody());
        Assertions.assertNotNull(guitarResponse.getBody().id());
        Assertions.assertEquals(manufacturer, guitarResponse.getBody().manufacturer());
        Assertions.assertEquals(model, guitarResponse.getBody().model());
        Assertions.assertEquals(finish, guitarResponse.getBody().finish());
        Assertions.assertEquals(serialNumber, guitarResponse.getBody().serialNumber());
        Assertions.assertEquals(amount, guitarResponse.getBody().amount());
    }

    @Given("there are following guitars in the database")
    public void givenGuitarsExistInDatabase(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        var guitars = new ArrayList<Guitar>();
        for (Map<String, String> columns : rows) {
            var guitar = new Guitar(
                    null,
                    columns.get("manufacturer"),
                    columns.get("model"),
                    columns.get("finish"),
                    columns.get("serial_number"),
                    new BigDecimal(columns.get("amount"))
            );

            guitars.add(guitar);
        }

        guitarRepository.saveAll(guitars);
    }

    @When("the client makes GET request on endpoint {string}")
    public void whenClientCallsGetGuitars(String path) {
        guitarsResponse = restTemplate.exchange(
                "http://localhost:" + this.port + path,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Guitar>>() {
                });
    }

    @Then("response contains {int} guitars")
    public void responseBodyContainsThreeGuitars(int expectedInt) {
        Assertions.assertNotNull(guitarsResponse.getBody());
        Assertions.assertEquals(expectedInt, guitarsResponse.getBody().size());
    }

}
