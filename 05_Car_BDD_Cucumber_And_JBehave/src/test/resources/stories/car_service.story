Scenario: Service of a car that requires this

Given Car Volvo XC60 requires service
When Car mileage should be updated by 10000
Then Service of the car has been made and its mileage has been updated
