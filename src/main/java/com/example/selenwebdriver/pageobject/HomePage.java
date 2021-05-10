package com.example.selenwebdriver.pageobject;

import com.epam.jdi.light.elements.init.PageFactory;
import org.openqa.selenium.WebDriver;

public class HomePage {
    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }
}
