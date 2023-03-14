Feature: Running tests for isBusinessDay endpoint

  Scenario: Calling isBusinessDay endpoint with date which is business day in USA
    When user calls isBusinessDay with date '2023-03-14' with country ''
    Then user get result as 'success' with with business indicator as 'true'

  Scenario: Calling isBusinessDay endpoint with empty date
    When user calls isBusinessDay with date '' with country ''
    Then user get result as 'failed' with with error message 'A valid date is required'

  Scenario: Calling isBusinessDay endpoint with date which is NOT a business day in USA
    When user calls isBusinessDay with date '2023-03-12' with country ''
    Then user get result as 'success' with with business indicator as 'false'

  Scenario: Calling isBusinessDay endpoint by providing string instead of date
    When user calls isBusinessDay with date 'test' with country ''
    Then user get result as 'failed' with with error message 'A valid date is required'

  Scenario: Calling isBusinessDay endpoint with date wit format dd-mm-YYYY
    When user calls isBusinessDay with date '12-03-2023' with country ''
    Then user get result as 'failed' with with error message 'A valid date is required'

  Scenario: Calling isBusinessDay endpoint with by providing date which is business day for specified country
    When user calls isBusinessDay with date '2023-03-14' with country 'GB'
    Then user get result as 'success' with with business indicator as 'true'


  Scenario: Calling isBusinessDay endpoint with by providing date which is NOT a business day for specified country
    When user calls isBusinessDay with date '2023-03-12' with country 'GB'
    Then user get result as 'success' with with business indicator as 'false'