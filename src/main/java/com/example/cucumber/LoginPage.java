package com.example.cucumber;

import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;

    public LoginPage(String baseUrl) {
        driver.get(baseUrl);
    }

}
