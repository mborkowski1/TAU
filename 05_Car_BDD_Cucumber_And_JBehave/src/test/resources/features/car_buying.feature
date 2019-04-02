Feature: Car buying
  Customer buys a car

  Scenario: Customer buys a car
    Given Customer chooses a car
    When Customer chose brand "Volvo"
    And Customer chose model "XC60"
    Then Car has been sold
