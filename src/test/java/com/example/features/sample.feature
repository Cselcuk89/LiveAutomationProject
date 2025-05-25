Feature: Sample API Tests

  Background:
    * url 'https://jsonplaceholder.typicode.com'

  Scenario: Get a user by ID
    Given path 'users/1'
    When method get
    Then status 200
    And match response.name == 'Leanne Graham'
    And match response.email == 'Sincere@april.biz'

  Scenario: Get all users and verify the first user
    Given path 'users'
    When method get
    Then status 200
    And match response[0].name == 'Leanne Graham'
    And match response[0].email == 'Sincere@april.biz'
