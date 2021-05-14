package com.example.selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TC_MainPage {
    private final MainPage mainPage = new MainPage();

    @BeforeClass
    public static void setUpAllure() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeClass
    public void setUp() {
        Configuration.startMaximized = true;
        open("https://www.jetbrains.com/");
    }

    @Test
    public void search() {
        mainPage.searchButton.click();

        $(byId("header-search")).sendKeys("Selenium");
        $(byXpath("//button[@type='submit' and text()='Search']")).click();

        $(byClassName("js-search-input")).shouldHave(attribute("value", "Selenium"));
    }

    @Test
    public void toolsMenu() {
        mainPage.toolsMenu.hover();

        $(byClassName("menu-main__popup-wrapper")).shouldBe(visible);
    }

    @Test
    public void navigationToAllTools() {
        mainPage.seeAllToolsButton.click();

        $(byClassName("products-list")).shouldBe(visible);

        // JUnit 形参 1 是 expected ，比较符合人脑思维习惯，而 TestNG 形参 1 反而是 actual
        Assert.assertEquals("All Developer Tools and Products by JetBrains", Selenide.title());
    }
}
