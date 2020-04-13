Feature: Item Creation
   I can do create a new item.
  
  Scenario: Successful creation (Positive)
    Given I am logged as "admin"
    When I go to the item creation form
    And Fill the form correctly
    Then a new Item is created
    
  Scenario: Trying to create a Item as a summoner (Negative)
  	Given I am logged as "summoner1"
    When I go to the item creation form
    Then I am back at the index page
    
  Scenario: Creation failed because there are errors in the form (Negative)
  	Given I am logged as "admin"
  	When I go to the item creation form
    And Fill the item creation form with wrong information
    Then I am back at the form page