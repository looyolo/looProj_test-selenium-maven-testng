package com.example.selenwebdriver;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;

/**
 * 测试场景：
 *   Web 测试项目经常包含浏览器兼容性相关测试，工作重复性相当高，
 *   需要经常考虑如何一次线执行多个浏览器的兼容性测试用例
 * 测试用例说明：
 *   分别使用 Firefox、Chrome 浏览器，TestNG 以并发方式在搜狗首页中搜索某个关键字
 * 代码解释：
 *   TestNG 提供了并发执行测试用例的功能，"testng_for_selenwebdriver-VisitSogouByMultipleBrowser.xml"中
 *   的配置内容设置了测试用例的并发执行参数和方式
 * 运行方法：
 *   右键单击当前工程中的"testng_for_selenwebdriver-VisitSogouByMultipleBrowser.xml"，选择 Run As TestNG Suite
 * 运行效果：
 *   在运行过程中，可以看到桌面同时弹出 Firefox、Chrome 浏览器窗口口，
 *   并在窗口中运行测试脚本定义的操作步骤
 */
public class VisitSogouByMultipleBrowser {
    WebDriver driver;
    String baseUrl = "http://www.sogou.com";

    // SelenideLogger 添加监听器 监听 AllureSelenide 日志
    @BeforeClass
    public static void setUpAllure() { SelenideLogger.addListener("allure",new AllureSelenide()); }

    @Parameters("browser")
    @BeforeClass
    public void setUp(String browser) throws Exception {
        if (browser.equalsIgnoreCase("firefox")) {
            // 指定 浏览器驱动程序 的 存放路径，并添加为系统属性值
//            System.setProperty("webdriver.firefox.bin","%FIREFOX_HOME%\\\\firefox.exe")
            System.setProperty("webdriver.gecko.driver","D:\\honor_selenium\\WebDriver_put_here\\geckodriver.exe");
            driver = new FirefoxDriver();
//            // 打开浏览器，使用"无头模式"，不会在桌面上打开，只在内存上运行
//            FirefoxOptions firefoxOption = new FirefoxOptions();
//            firefoxOption.addArguments("-headless");
//            driver = new FirefoxDriver(firefoxOption);
        }
        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver","D:\\honor_selenium\\WebDriver_put_here\\chromedriver.exe");
            driver = new ChromeDriver();
//            ChromeOptions chromeOption = new ChromeOptions();
//            chromeOption.addArguments("-headless");
//            driver = new ChromeDriver(chromeOption);
        }
    }

    @AfterMethod
    public void tearDown() {
        try {
            // Nothin to do
        } finally {
            // 关闭浏览器
            driver.quit();
        }
    }

    @Test
    public void visitSogou() throws InterruptedException {
        // 打开 ”搜狗搜索“ 首页
        driver.get(baseUrl + "/");
        // 在搜索框中，输入"搜狗搜索"
        driver.findElement(By.xpath("//*[@id=\"query\"]")).sendKeys("搜狗搜索", Keys.ENTER);
        //  等价于
//        WebElement inputBox = driver.findElement(By.xpath("//*[@id=\"query\"]"));
//        Assert.assertTrue(inputBox.isDisplayed());
//        inputBox.sendKeys("搜狗搜索");
        // 点击"搜索"按钮
        driver.findElement(By.xpath("//*[@id=\"stb\"]")).click();

        // 代码段可选，主要用于等待 2 秒，让页面完成显示过程，查看操作后的结果
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 判断搜索结果中，是否包含期望的关键字
        Assert.assertTrue(driver.getPageSource().contains("搜狗"));
    }

}
