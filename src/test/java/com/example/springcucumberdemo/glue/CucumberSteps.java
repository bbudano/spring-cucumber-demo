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

    private final GuitarRepository guitarRepository;

    @LocalServerPort
    private String port;

    private HttpEntity<Guitar> requestEntity;

    private ResponseEntity<?> response;

    private Long existingGuitarId;

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
    public void whenClientCallsCreateGuitar(String path) {
        response = restTemplate.exchange(
                "http://localhost:" + this.port + path,
                HttpMethod.POST,
                requestEntity,
                Guitar.class);
    }

    @Then("response status code is {int}")
    public void thenStatusCode(int expectedStatusCode) {
        Assertions.assertEquals(expectedStatusCode, response.getStatusCode().value());
    }

    @And("response contains manufacturer {string} and model {string} and finish {string} and serial number {string} and amount {bigdecimal}")
    public void responseBodyMatches(String manufacturer, String model, String finish, String serialNumber, BigDecimal amount) {
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody() instanceof Guitar);
        var response = (Guitar) this.response.getBody();

        Assertions.assertNotNull(response.id());
        Assertions.assertEquals(manufacturer, response.manufacturer());
        Assertions.assertEquals(model, response.model());
        Assertions.assertEquals(finish, response.finish());
        Assertions.assertEquals(serialNumber, response.serialNumber());
        Assertions.assertEquals(amount, response.amount());
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
        response = restTemplate.exchange(
                "http://localhost:" + this.port + path,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Guitar>>() {
                });
    }

    @Then("response contains {int} guitars")
    public void responseBodyContainsThreeGuitars(int expectedInt) {
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody() instanceof ArrayList);

        var response = (ArrayList) this.response.getBody();

        Assertions.assertEquals(expectedInt, response.size());
    }

    @Given("a guitar exists in the database with manufacturer {string} and model {string}" +
            " and finish {string} and serial number {string} and amount {bigdecimal}")
    public void givenGuitarExistsInDatabase(String manufacturer, String model, String finish, String serialNumber, BigDecimal amount) {
        var guitar = new Guitar(null, manufacturer, model, finish, serialNumber, amount);

        guitar = guitarRepository.save(guitar);

        existingGuitarId = guitar.id();

        System.out.println(existingGuitarId);

    }

    @When("the client makes GET request on endpoint {string} with existing guitar id")
    public void whenClientCallsGetGuitar(String path) {
        response = restTemplate.exchange(
                "http://localhost:" + this.port + path,
                HttpMethod.GET,
                null,
                Guitar.class,
                Map.of("id", existingGuitarId));
    }

    @When("the client makes DELETE request on endpoint {string} with existing guitar id")
    public void whenClientCallsDeleteGuitar(String path) {
        response = restTemplate.exchange(
                "http://localhost:" + this.port + path,
                HttpMethod.DELETE,
                null,
                Void.class,
                Map.of("id", existingGuitarId));
    }

    @And("response is empty")
    public void responseIsEmpty() {
        Assertions.assertNull(response.getBody());
    }

}
