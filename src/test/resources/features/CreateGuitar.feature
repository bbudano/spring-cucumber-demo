Feature: Client can create a new guitar
    Scenario: Client makes POST request to /guitars with valid request body
        Given the client wants to create a guitar with manufacturer "Chapman" and model "ML2 Pro" and finish "River Styx Black" and serial number "12345" and amount 1245.00
        When the client makes POST request on endpoint "/guitars"
        Then response status code is 201
        And response body contains manufacturer "Chapman" and model "ML2 Pro" and finish "River Styx Black" and serial number "12345" and amount 1245.00
