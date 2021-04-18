package com.example.selenwebdriver;

import com.codeborne.selenide.logevents.SelenideLogger;
import com.epam.jdi.light.elements.common.Mouse;
import io.qameta.allure.selenide.AllureSelenide;
import javafx.scene.input.MouseButton;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * 代码解释：
 *   举例演示 WebDriver 常用 API 的使用方法
 */
public class WebDriverApiExamples {
    WebDriver driver;
    String baseUrl1, baseUrl2;

    // SelenideLogger 添加监听器 监听 AllureSelenide 日志
    @BeforeClass
    public static void setUpAllure() { SelenideLogger.addListener("allure", new AllureSelenide()); }

    @Parameters({"browser", "whichDriver", "whereDriver"})
    @BeforeClass
    public void setUp(String browser, String whichDriver, String whereDriver) {
        if (browser.equalsIgnoreCase("firefox")) {
            // 指定 浏览器驱动程序 的 存放路径，并添加为系统属性值
            System.setProperty(whichDriver, whereDriver);
            driver = new FirefoxDriver();
//            // 打开浏览器，使用"无头模式"，不会在桌面上打开，只在内存上运行
//            driver = new FirefoxDriver(new FirefoxOptions().addArguments("-headless"));
        }
        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty(whichDriver, whereDriver);
            driver = new ChromeDriver();
//            driver = new ChromeDriver(new ChromeOptions().addArguments("-headless"));
        }
    }

    @AfterClass
    public void tearDown() {
        try {
            // Nothing to do
        } finally {
            // 关闭浏览器
            driver.quit();
        }
    }

//    /* 1: 访问某网页地址 */
//    @Parameters("baseUrl1")
//    @Test
//    public void visitBaseUrl(String baseUrl1) {
//        // 方法一
////        driver.get(baseUrl1 + "/");
//        // 方法二
//        driver.navigate().to(baseUrl1 + "/");
//    }

//    /* 2: 返回访问上一个网页，又前进到下一个网页,并刷新当前页面（模拟单击浏览器的 后退、前进、重新加载 功能）*/
//    @Parameters({"baseUrl1", "baseUrl2"})
//    @Test
//    public void visitRecentUrl(String baseUrl1, String baseUrl2) {
//        driver.navigate().to(baseUrl1);
//        driver.navigate().to(baseUrl2);
//        driver.navigate().back();
//        driver.navigate().forward();
//        driver.navigate().refresh();
//    }

//    /* 3: 操作浏览器窗口 */
//    @Parameters("baseUrl1")
//    @Test
//    public void operateBrowserWindow(String baseUrl1) {
//        // 定义一个 Point 对象，两个 150 表示屏幕上位置相对于屏幕左上角 (0,0) 的横坐标距离、纵坐标距离
//        Point point = new Point(150,150);
//        // 定义一个 Dimension 对象，两个 500 表示屏幕上窗口的长度和宽度
//        Dimension dimension = new Dimension(500,500);
//        // 设定浏览器的位置为 point 对象的坐标，此方法对某些版本浏览器失效
//        driver.manage().window().setPosition(point);
//        // 设定浏览器窗口的大小为 dimension 对象的长和宽，此方法对某些版本浏览器失效
//        driver.manage().window().setSize(dimension);
//        // 获取浏览器在屏幕上的位置，此方法对某些版本浏览器失效
//        System.out.println(driver.manage().window().getPosition());
//        // 获取浏览器窗口的大小，此方法对某些版本浏览器失效
//        System.out.println(driver.manage().window().getSize());
//        // 设定浏览器窗口最大化
//        driver.manage().window().maximize();
//        driver.get(baseUrl1 + "/");
//    }

//    /* 4：获取页面的 Title 属性、页面源代码、当前页面的 URL 地址 等信息*/
//    @Parameters("baseUrl1")
//    @Test
//    public void getPageInfo(String baseUrl1) {
//        driver.get(baseUrl1 +"/");
//        // 获取页面的 Title 属性，并断言它是不是"搜狗搜索引擎 - 上网从搜狗开始"
//        String pageTitle = driver.getTitle();
//        System.out.println("页面 Title 是 " + pageTitle);
//        Assert.assertEquals("搜狗搜索引擎 - 上网从搜狗开始",pageTitle);
//        // 获取页面的 源代码，并断言它是不是包含"购物"关键字
//        String pageSource = driver.getPageSource();
//        System.out.println("页面 源代码 是 " + pageSource);
//        Assert.assertTrue(pageSource.contains("购物"));
//        // 页面上点击"更多->购物"，进入购物主页
//        driver.findElement(By.xpath("//*[@id=\"more-product\"]")).click();
//        driver.findElement(By.xpath("//*[@id=\"index_more_gouwu\"]")).click();
//        // 获取页面 URL 地址，并断言它是否为"http://www.sogou.com"
//        String currentUrl = driver.getCurrentUrl();
//        System.out.println("页面 URL 是 " + currentUrl);
//        Assert.assertEquals("http://www.sogou.com", currentUrl);
//    }

//    /* 5: 清除输入框中原有的内容，重新输入指定的内容 */
//    @Parameters("baseUrl1")
//    @Test
//    public void operateInputBoxText(String baseUrl1) {
//        driver.get(baseUrl1 + "/");
//        WebElement inputBox = driver.findElement(By.xpath("//*[@id=\"query\"]"));
//        // 清除输入框中原有的内容
//        inputBox.clear();
//        // 重新输入指定的内容
//        String inputString = "指定的";
//        inputBox.sendKeys(inputString, Keys.ENTER);
//
//        // 代码段可选，主要用于等待 2 秒，让页面完成显示过程，查看操作后的结果
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

//    /* 6.1: 发送键盘组合键，模拟"全选 Ctrl+A ->复制 Ctrl+C ->粘贴 Ctrl+V"快捷键功能
//    *         第 1 种方法: 通过 Actions 类实现
//    * */
//    @Parameters("baseUrl1")
//    @Test
//    public void ActionsCtrlACV(String baseUrl1) throws InterruptedException, AWTException {
//        driver.get(baseUrl1 + "/");
//        WebElement inputBox = driver.findElement(By.xpath("//*[@id=\"query\"]"));
//        inputBox.clear();
//        inputBox.sendKeys("搜狗");
//
//        Thread.sleep(2000);
//        // 定义一个 Actions 对象
//        Actions action = new Actions(driver);
//        // 模拟 全选 Ctrl+A
//        action.keyDown(Keys.CONTROL);  // 按下 Ctrl 键
//        action.sendKeys(Keys.chord("A"));  // 按下字母键，这里大小写均可
//        action.keyUp(Keys.CONTROL);  // 释放 Ctrl 键
//        // 模拟 复制 Ctrl+C
//        action.keyDown(Keys.CONTROL);  // 按下 Ctrl 键
//        action.sendKeys(Keys.chord("c"));  // 按下字母键，这里大小写均可
//        action.keyUp(Keys.CONTROL);  // 释放 Ctrl 键
//        // 这一步，必不可少，作用是使得以上组合键生效
//        action.build().perform();
//
//        Thread.sleep(2000);
//
//        driver.navigate().to("http://www.baidu.com" + "/");
//        inputBox = driver.findElement(By.xpath("//*[@id=\"kw\"]"));
//        inputBox.clear();
//
//        // 模拟 粘贴 Ctrl+V
//        action.keyDown(Keys.CONTROL);  // 按下 Ctrl 键
//        action.sendKeys(Keys.chord("v"));  // 按下字母键，这里大小写均可
//        action.keyUp(Keys.CONTROL);  // 释放 Ctrl 键
//        // 这一步，必不可少，作用是使得以上组合键生效
//        action.build().perform();
//
//        Thread.sleep(2000);
//    }
//
//    /* 6.2: 发送键盘组合键，模拟"全选 Ctrl+A ->复制 Ctrl+C ->粘贴 Ctrl+V"快捷键功能
//     *         第 2 种方法: 通过 Robot 类实现
//     * */
//    @Parameters("baseUrl1")
//    @Test
//    public void RobotCtrlACV(String baseUrl1) throws InterruptedException, AWTException {
//        driver.get(baseUrl1 + "/");
//        WebElement inputBox = driver.findElement(By.xpath("//*[@id=\"query\"]"));
//        inputBox.clear();
//        inputBox.sendKeys("搜狗");
//
//        Thread.sleep(2000);
//        // 定义一个 Robot 对象
//        Robot robot = new Robot();
//        // 模拟 全选 Ctrl+A
//        robot.keyPress(KeyEvent.VK_CONTROL);  // 按下 Ctrl 键
//        robot.keyPress(KeyEvent.VK_A);  // 按下字母键，这里大小写均可
//        robot.keyRelease(KeyEvent.VK_A);  // 释放字母键，这里大小写均可
//        robot.keyRelease(KeyEvent.VK_CONTROL);  // 释放 Ctrl 键
//        // 模拟 复制 Ctrl+C
//        robot.keyPress(KeyEvent.VK_CONTROL);  // 按下 Ctrl 键
//        robot.keyPress(KeyEvent.VK_C);  // 按下字母键，这里大小写均可
//        robot.keyRelease(KeyEvent.VK_C);  // 释放字母键，这里大小写均可
//        robot.keyRelease(KeyEvent.VK_CONTROL);  // 释放 Ctrl 键
//
//        Thread.sleep(2000);
//
//        driver.navigate().to("http://www.baidu.com" + "/");
//        inputBox = driver.findElement(By.xpath("//*[@id=\"kw\"]"));
//        inputBox.clear();
//
//        // 模拟 粘贴 Ctrl+V
//        robot.keyPress(KeyEvent.VK_CONTROL);  // 按下 Ctrl 键
//        robot.keyPress(KeyEvent.VK_V);  // 按下字母键，这里大小写均可
//        robot.keyRelease(KeyEvent.VK_V);  // 释放字母键，这里大小写均可
//        robot.keyRelease(KeyEvent.VK_CONTROL);  // 释放 Ctrl 键
//
//        Thread.sleep(2000);
//    }


}
