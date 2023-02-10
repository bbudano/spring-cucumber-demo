Feature: Client can delete guitar by id
  Scenario: Client makes DELETE request to /guitars/{id}
    Given a guitar exists in the database with manufacturer "Chapman" and model "ML2 Pro" and finish "River Styx Black" and serial number "12345" and amount 1245.00
    When the client makes DELETE request on endpoint "/guitars/{id}" with existing guitar id
    Then response status code is 200
    And response is empty