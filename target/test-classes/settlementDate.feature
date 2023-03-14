Feature: Running tests for settlementDate endpoint

  Scenario: Calling settlementDate endpoint with date and without delay
    When user calls settlementDate with date '2023-03-14' and delay '' and country ''
    Then user get status code '500'