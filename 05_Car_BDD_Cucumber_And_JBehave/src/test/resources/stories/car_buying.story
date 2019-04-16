Scenario: Customer buys a car

Given Customer chooses a car
When Customer chose brand <brand>
And Customer chose model <model>
Then Car has been sold

Examples:
| brand | model |
| Volvo | XC60  |
| Audi  | A4    |
| BMW   | X5    |
