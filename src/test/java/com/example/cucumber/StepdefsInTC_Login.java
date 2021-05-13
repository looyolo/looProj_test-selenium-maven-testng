package com.example.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepdefsInTC_Login {
    @Given("Alice is hungry")
    public Boolean aliceIsHungry() {
        System.out.println("Alice 是 饿了");

        return true;
    }

    @When("she eats {int} cucumbers")
    public Boolean sheEatsCucumbers(int arg0) {
        System.out.println("她 吃了 3 根 小黄瓜");

        return true;
    }

    @Then("she will be full")
    public Boolean sheWillBeFull() {
        System.out.println("她 会饱的");

        return true;
    }
}
