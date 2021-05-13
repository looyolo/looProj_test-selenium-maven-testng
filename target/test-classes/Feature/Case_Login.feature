# Comment: https://cucumber.io/tools/cucumber-open/
@simpleDemo
Feature: Eating too many cucumbers may not be good for you

  Scenario: Eating a few is no problem
    Given Alice is hungry
    When she eats 3 cucumbers
    Then she will be full