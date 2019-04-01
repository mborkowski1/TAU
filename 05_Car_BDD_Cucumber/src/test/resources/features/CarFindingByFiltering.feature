Feature: Car finding by filtering
  User is looking for a cars by filtering

  Scenario: User is looking for a car by filtering
    Given User is on page with cars search engine
    When User sets the brand filtering to "Audi"
    And User sets the model filtering to "A4"
    And User sets max mileage limit filtering to 50000
    And User sets min manufacture year filtering to 2018
    Then User finds cars that meet the criteria given by him
