Feature: Client can create a new guitar
    Scenario: Client makes call to POST /guitars
        When the client calls endpoint "/guitars"
        Then response status code is 201
        And response body contains manufacturer "Chapman" and model "ML2 Pro" and finish "River Styx Black" and serial number "12345" and amount 1245.00