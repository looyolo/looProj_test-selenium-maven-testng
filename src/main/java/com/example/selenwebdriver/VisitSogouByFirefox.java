package com.example.selenwebdriver;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 使用 Firefox 浏览器进行测试
 */
public class VisitSogouByFirefox {
    WebDriver driver;
    String baseUrl;

    // SelenideLogger 添加监听器 监听 AllureSelenide 日志
    @BeforeClass
    public static void setUpAllure() { SelenideLogger.addListener("allure",new AllureSelenide()); }

    @BeforeMethod
    public void setUp() throws Exception {
        // 指定 浏览器驱动程序 的 存放路径，并添加为系统属性值
        System.setProperty("webdriver.gecko.driver","D:\\honor_selenium\\WebDriver_put_here\\geckodriver.exe");
        /**
         * 若 WebDriver 无法加载 Firefox 浏览器驱动，才需要添加这一行代码设定 Firefox 浏览器的所在路径
         */
//        System.setProperty("webdriver.firefox.bin","%FIREFOX_HOME%\\firefox.exe")
        // 打开浏览器，使用"无头模式"，不会在桌面上打开，只在内存上运行
        FirefoxOptions firefoxOption = new FirefoxOptions();
        firefoxOption.addArguments("-headless");
        driver = new FirefoxDriver(firefoxOption);
        baseUrl = "http://www.sogou.com";
    }

    @AfterMethod
    public void tearDown() {
        try {
            // Nothing to do
        } finally {
            // 关闭浏览器
            driver.quit();
        }
    }

    @Test
    public void visitSogou() { /* 打开搜狗首页 */ driver.get(baseUrl + "/"); }

}
