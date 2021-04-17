package com.example.selenwebdriver;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 使用 Chrome 浏览器进行测试
 */
public class VisitSogouByChrome {
    WebDriver driver;
    String baseUrl;

    // SelenideLogger 添加监听器 监听 AllureSelenide 日志
    @BeforeClass
    public static void setUpAllure() { SelenideLogger.addListener("allure",new AllureSelenide()); }

    @BeforeMethod
    public void setUp() throws Exception {
        // 指定 浏览器驱动程序 的 存放路径，并添加为系统属性值
        System.setProperty("webdriver.chrome.driver", "D:\\honor_selenium\\WebDriver_put_here\\chromedriver.exe");
        // 打开浏览器，使用"无头模式"，不会在桌面上打开，只在内存上运行
        ChromeOptions chromeOption = new ChromeOptions();
        chromeOption.addArguments("-headless");
        driver = new ChromeDriver(chromeOption);
        baseUrl = "http://www.sogou.com";
    }

    @Test
    public void VisitSogou() { /* 打开搜狗首页 */ driver.get(baseUrl + "/"); }

    @AfterMethod
    public void teardown() throws Exception {
        try {
            // Nothiing to do
        } finally {
            // 关闭浏览器
            driver.quit();
        }
    }
}
