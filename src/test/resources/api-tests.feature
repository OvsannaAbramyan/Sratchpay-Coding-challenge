Feature: Testing provided apis

  Background: User generates authorization token
    Given User Authorized

  Scenario: Authorization check for /clinics/{practiceId}/emails
    When The logged in user tries to get a list of email addresses of practice id 2
    Then User should be prevented from getting the list

  Scenario: Searching clinics by term with given authorization
    When The logged in user tries to get a clinics by term 'veterinary'
    Then User able to get clinics

  Scenario: Searching clinics by term without given authorization
    When Not logged in user tries to get a clinics by term 'veterinary'
    Then User should be prevented from getting the clinics