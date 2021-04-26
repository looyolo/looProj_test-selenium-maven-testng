package com.example.selenwebdriver;

import com.codeborne.selenide.logevents.SelenideLogger;
import com.epam.jdi.light.logger.AllureLogger;

import io.qameta.allure.selenide.AllureSelenide;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/*
* 代码解释：
*     举例演示 WebDriver 高级应用
*  */
public class WebDriverSeniorPractice {
    WebDriver driver;

    @BeforeClass
    public static void setUpAllure() {
//        // SelenideLogger 添加监听器 监听 AllureSelenide 日志
//        SelenideLogger.addListener("allure", new AllureSelenide());
        // LoggerFactory 类提供的 Logger 接口，来自 org.slf4j:slf4j-api:1.7.30
        Logger logger = LoggerFactory.getLogger(AllureLogger.class);
        logger.debug("allure");
    }

    @Parameters({"browser", "whichDriver" , "whereDriver"})
    @BeforeClass
    public void setUp(String browser, String whichDriver , String whereeDriver) {
        if (browser.equalsIgnoreCase("firefox")) {
            // 指定 浏览器驱动程序 的 存放路径，并添加到系统属性中
            System.setProperty(whichDriver, whereeDriver);
//            driver = new FirefoxDriver();
            // 打开浏览器，使用"无头模式"，不会在桌面上打开，只在内存上运行
            driver = new FirefoxDriver(new FirefoxOptions().addArguments("-headless"));
        }
        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty(whichDriver, whereeDriver);
            driver = new ChromeDriver();
//            driver = new ChromeDriver(new ChromeOptions().addArguments("-headless"));
        }
    }

    @AfterClass
    public void teardown() {
        try {
            // Nothing to do
        } finally {
            // 关闭浏览器
            driver.quit();
        }
    }

    /* 1：使用 JavascriptExecutor 进行页面元素的单击操作
    *         在某些情况下，页面元素的 click 操作无法生效，可以用这个方法来解决
    *
    *         把常用的操作写在一个函数方法里，减少冗余代码的编写，方便重复调用，这就是"封装"的作用
    *  */
    @Parameters("baseUrl1")
    @Test
    public void testJavascriptExecuteClick(String baseUrl1) throws InterruptedException {
        driver.get(baseUrl1 + "/");

        WebElement searchInputBox = driver.findElement(By.xpath("//*[@id=\"query\"]"));
        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"stb\"]"));
        searchInputBox.sendKeys("使用 JavascriptExecutor 进行页面元素的单击操作");
        // 调用 封装好的 javascriptExecuteClick 执行单击"搜狗搜索"按钮
        javascriptExecuteClick(searchButton);
    }
    // 封装好的 javascriptExecuteClick
    protected void javascriptExecuteClick(WebElement webElement) {
        try {
            // 判断 函数入参 webElement 是否显示在页面中，以及 是否处于使能状态
            if (webElement.isDisplayed() && webElement.isEnabled()) {
                System.out.println("使用 JavascriptExecutor 进行页面元素的单击操作");
                ((JavascriptExecutor)driver).executeScript("arguments[0].click()",webElement);
            }
        } catch (StaleElementReferenceException e) {
            System.out.println("页面元素引用异常：");
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            System.out.println("页面元素不存在：");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("单击操作无法完成：");
            e.printStackTrace();
        }
    }

    /* 2：在使用 Ajax 方式产生的浮动框中，单击选择包含某个关键字的选项
    *
    *
    *  */
    @Parameters("baseUrl1")
    @Test
    public void testAjaxSuggestionOption(String baseUrl1) throws InterruptedException {
        driver.get(baseUrl1 + "/");

        WebElement searchInputBox = driver.findElement(By.xpath("//*[@id=\"query\"]"));
        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"stb\"]"));
        String keywordsPart1 = "";
        String keywordsPart2 = "争议";
        searchInputBox.sendKeys(keywordsPart1);
        searchInputBox.click();
        // 延长页面停顿时间，保证 浮动框内 的选项内容 可以被获取得到
        Thread.sleep(2000);
        List<WebElement> suggestionOptions = null;

        try {
            // 判断页面元素是否存在，这里使用 定制化显示等待
            WebDriverWait webDriverWait = new WebDriverWait(driver,2);
            // 将浮动框中的"今日热词"选项，存放到一个 List 容器中
            suggestionOptions = webDriverWait.until((new ExpectedCondition<List<WebElement>>() {
                @Override
                public @Nullable List<WebElement> apply(@Nullable WebDriver webDriver) {
                    //  调用 driver.findElements 返回一个 List<WebElement> 集合
                    return webDriver.findElements(By.xpath("//*[@id=\"vl\"]//ul/li"));
                }
            }));
        } catch (Exception e) {
            Assert.fail("FAILED, please check your location expression....");
            e.printStackTrace();
        }

        // 容器不为空的话，遍历所有"今日热词"选项，判断 哪一个包含 keywordsPart2 ，则单击后会自动显示在输入框中，进而点击"搜索"按钮
        if (!suggestionOptions.isEmpty()) {
            System.out.println("\"今日热词\"选项个数：" + suggestionOptions.size());
            for (WebElement suggestionOption : suggestionOptions) {
                if (suggestionOption.getText().contains(keywordsPart2)) {
                    System.out.println("选中的\"今日热词\"选项：" + suggestionOption.getText());
                    suggestionOption.click();
                    searchButton.click();
                    break;
                }
            }
        }
        Thread.sleep(2000);
    }





}
