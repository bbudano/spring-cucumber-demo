Feature: Client can fetch list of guitars
  Scenario: Client makes GET request to /guitars
    Given there are following guitars in the database
      | manufacturer | model          | finish           | serial_number | amount  |
      | Chapman      | ML2 Pro        | River Styx Black | 12345         | 1245.00 |
      | PRS          | SE McCarty 594 | Faded Blue       | 12346         | 1049.00 |
    When the client makes GET request on endpoint "/guitars"
    Then response contains 2 guitars