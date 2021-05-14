package com.example.cucumber;

import com.epam.jdi.light.elements.init.PageFactory;
import org.openqa.selenium.WebDriver;

/*
 * 这是 package com.example.selenwebdriver.pageobject.HomePage 的一个副本。复制于 2021-05-14 16:41
 *
 * 使用目的：
 *     用于演示 "BDD + POM " ( 行为驱动测试 + Page Object 面向对象编程模式）高级 自动化测试框架
 *
 * 代码解释：
 *     自动化测试执行和管理模块 使用的是 JUnit ，而不是 TestNG
 *
 *  */
public class HomePage_Copy_1 {
    WebDriver driver;

    public HomePage_Copy_1(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }
}