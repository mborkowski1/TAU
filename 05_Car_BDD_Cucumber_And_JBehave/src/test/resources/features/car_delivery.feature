Feature: Car delivery
  Delivery of new cars to the car dealership

  Scenario: Delivery of cars
    Given Delivery of 3 cars
    When Cars has been delivered
    Then Quantity of cars has been increased by 3
