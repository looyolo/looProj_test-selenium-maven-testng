package com.example.selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;

public class MainPageTest_Sogou {
    // 创建 MainPage 对象，并用 final 修饰限定为不可修改
    private final MainPage_Sogou mainPage_Sogou = new MainPage_Sogou();

    // SelenideLogger 添加监听器 监听 AllureSelenide 日志
    @BeforeClass
    public static void setUpAllure() { SelenideLogger.addListener("allure",new AllureSelenide()); }

    // 设置 浏览器 ，并打开网站"搜狗搜索"
    @BeforeMethod
    public void setUp() {
        // 打开时，窗口最大化
        Configuration.startMaximized = true;
//        // 不打开窗口，使用"无头模式"，提高性能
//        Configuration.headless = true;
        // 停留 1 秒
        open("https://www.sogou.com/");
    }

    // 测试用例 1：点击 搜狗搜索 按钮
    @Test
    public void search() {
        mainPage_Sogou.sogou_Search.click();

        //  $(byXpath(String) 等价于  $(By.xpath(String))
        $(byXpath("//*[@id=\"query\"]")).sendKeys("搜狗搜索");
        sleep(3000);
        $(By.xpath("//*[@id=\"stb\"]")).click();
        sleep(3000);

    }

}
