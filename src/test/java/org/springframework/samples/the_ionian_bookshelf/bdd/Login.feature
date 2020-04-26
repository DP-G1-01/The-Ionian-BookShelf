Feature: Traditional Login 
   I can do login in the system with my username and password.
  
  Scenario: Successful login as summoner1 (Positive)
    Given I am not logged in the system
    When I do login as user "summoner1"
    Then "summoner1" appears as the current user
    
  Scenario: Login fail (Negative)
  	Given I am not logged in the system
    When I try to do login as user "summoner1" with an invalid password
    Then the login form is shown again