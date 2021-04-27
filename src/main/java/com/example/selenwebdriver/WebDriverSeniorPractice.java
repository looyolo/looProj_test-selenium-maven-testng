package com.example.selenwebdriver;

import com.codeborne.selenide.logevents.SelenideLogger;
import com.epam.jdi.light.driver.WebDriverFactory;
import com.epam.jdi.light.logger.AllureLogger;

import io.qameta.allure.selenide.AllureSelenide;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
            driver = new FirefoxDriver();
            // 打开浏览器，使用"无头模式"，不会在桌面上打开，只在内存上运行
//            driver = new FirefoxDriver(new FirefoxOptions().addArguments("-headless"));
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
    public void jsExecuteClick(String baseUrl1) throws InterruptedException {
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

    /* 2：使用 JavascriptExecutor 操作页面元素的属性
     *
     * */
    @Parameters("baseUrl1")
    @Test
    public void jsOperateElementAttribute(String baseUrl1) throws InterruptedException {
        driver.get(baseUrl1 + "/");

        WebElement textInputBox = driver.findElement(By.xpath("//*[@id=\"query\"]"));
        // 调用 封装好的 "增删改查" JavascriptExecutor
        try {
            jsSelectElementAttribute(driver, textInputBox, "id");
            jsUpdateElementAttribute(driver, textInputBox, "len", "40");
            jsAddElementAttribute(driver, textInputBox, "placeholder", "请输入你要搜索的关键词");
            jsDeleteElementAttribute(driver, textInputBox, "class");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(2000);
    }
    // 封装好的 "增删改查" JavascriptExecutor
    protected void jsSelectElementAttribute(WebDriver webDriver, WebElement webElement, String attributeName) {
        ((JavascriptExecutor)driver).executeScript(
                "arguments[0].getAttribute(arguments[1]);", webElement, attributeName);
        System.out.println("查询到属性 " + attributeName + " 的值：" + webElement.getAttribute(attributeName));
    }
    protected void jsDeleteElementAttribute(WebDriver webDriver, WebElement webElement, String attributeName) {
        System.out.println("属性 " + attributeName + " 将要被移除，移除时，它的值：" + webElement.getAttribute(attributeName));
        ((JavascriptExecutor)driver).executeScript(
                "arguments[0].removeAttribute(arguments[1]);", webElement, attributeName);
    }
    protected void jsAddElementAttribute(WebDriver webDriver, WebElement webElement, String attributeName, String attributeValue) {
        ((JavascriptExecutor)driver).executeScript(
                "arguments[0].setAttribute(arguments[1],arguments[2]);", webElement, attributeName, attributeValue);
        System.out.println("属性 " + attributeName + " 已添加，它的值：" + webElement.getAttribute(attributeName));
    }
    protected void jsUpdateElementAttribute(WebDriver webDriver, WebElement webElement, String attributeName, String attributeValue) {
        System.out.println("属性 " + attributeName + " 原来的值：" + webElement.getAttribute(attributeName));
        ((JavascriptExecutor)webDriver).executeScript(
                "arguments[0].setAttribute(arguments[1], arguments[2]);", webElement, attributeName, attributeValue);
        System.out.println("属性 " + attributeName + " 修改后的值：" + webElement.getAttribute(attributeName));
    }

    /* 3：在使用 Ajax 方式产生的浮动框中，单击选择包含某个关键字的选项
    *         有些 被测试的页面 包含 Ajax 局部刷新机制，并且会产生显示多条数据的浮动框，
    *          需要单击选择 浮动框中 包含某个关键词 的选项
    *         多见于 热搜、预览数据、保存的历史记录 等功能点
    *  */
    @Parameters("baseUrl1")
    @Test
    public void operateAjaxContent(String baseUrl1) throws InterruptedException {
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

    /* 4：无人看管，自动下载文件
     *         自动化实施过程中，经常会遇到在代码中设定了下载文件的 MIME 类型，但是测试程序执行时，依旧会显示下载弹出框，并且需要人为介入处理，
     *          产生上述情况的原因是，网站服务器中的一些文件定义为其他 MIME 类型，例如，一个 exe 文件被网站服务器定义为 "application/octet-stream"，
     *         如何获取下载文件的 MIME 类型呢？可以借助抓包工具 Charles 等，从 HTTP Request Header 中查看 Content-type 获悉。
     *
     *  */
    @Parameters({"browser", "downloadUrl1", "targetFile1", "downloadDirectory"})
    @Test
    public void downloadAutomatically(String browser, String downloadUrl1,
                                      String targetFile1, String downloadDirectory) throws InterruptedException {
        WebDriver driver_for_download = null;
        try {
            // 判断选择 chrome 浏览器下载目标文件
            if (browser.equalsIgnoreCase("chrome")) {
                driver_for_download = new ChromeDriver(setChromeDriverOptions(downloadDirectory));
                driver_for_download.get(downloadUrl1);
                driver_for_download.findElement(By.partialLinkText(targetFile1)).click();
            }
            // 判断选择 firefox 浏览器下载目标文件
            if (browser.equalsIgnoreCase("firefox")) {
                driver_for_download = new FirefoxDriver(setFirefoxDriverOptions(downloadDirectory));
                driver_for_download.get(downloadUrl1);
                driver_for_download.findElement(By.partialLinkText(targetFile1)).click();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 延迟页面停顿时间，等待下载完成后，再关闭浏览器
            Thread.sleep(5000);
            driver_for_download.quit();
        }
    }
    // 封装好的 Firefox 浏览器驱动配置 设定方法
    protected FirefoxOptions setFirefoxDriverOptions(String downloadDirectory) {
        /* Firefox 浏览器 地址栏输入：about:config
         *     查看 个性化配置项 的设定情况，有关下载的，示例如下：
         *          <1> browser.download.folderList 默认值（数值） 1 设定为下载文件保存到操作系统默认"下载"文件夹中，0 是桌面，2 是自己指定的
         *          <2> browser.download.dir 默认值 空字符串，设定为自己指定的下载文件保存目录
         *          <3> browser.helperApps.neverAsk.openFile 默认值 空字符串，填写下载文件的 MIME 类型，可以多种类型，逗号分隔，
         *                 设定为直接打开下载文件，不显示确认框。MIME 类型可以访问 https://tool.oschina.net/commons 查看 HTTP Content-type 对照表
         *          <4> browser.helperApps.neverAsk.saveToDisk 默认值 空字符串，填写下载文件的 MIME 类型，可以多种类型，逗号分隔，
         *                 设定为直接保存下载文件到磁盘中。MIME 类型可以访问 https://tool.oschina.net/commons 查看 HTTP Content-type 对照表
         *          <5> browser.helperApps.alwaysAsk.force 默认值 true，设定是否针对未知的 MIME 文件会弹出窗口让用户处理
         *          <6> browser.download.manager.alertOnEXEOpen 默认值 true，设定下载 exe 文件是否弹出警告
         *          <7> browser.download.manager.showWhenStarting 默认值 true，设定用户启动下载时，是否显示文件下载窗口
         *          <8> browser.download.manager.useWindow 默认值 true，设定下载是否显示下载框
         *          <9> browser.download.manager.focusWhenStarting 默认值 true，设定下载框在下载时会获取焦点
         *          <10> browser.download.manager.showAlertWhenComplete 默认值 true，设定是否显示下载完成提示框
         *          <11> browser.download.manager.closeWhenDone 默认值 true，设定下载结束后，是否自动关闭下载框
         *
         *  */
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("browser.download.folderList", 2);
        firefoxProfile.setPreference("browser.download.dir", downloadDirectory);
        firefoxProfile.setPreference("browser.helperApps.neverAsk.openFile", "");
        firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream");
        firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
        firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
        firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
        firefoxProfile.setPreference("browser.download.manager.useWindow", false);
        firefoxProfile.setPreference("browser.download.manager.focusWhenStarting", false);
        firefoxProfile.setPreference("browser.download.manager.showAlertWhenComplete", false);
        firefoxProfile.setPreference("browser.download.manager.closeWhenDone", false);
        firefoxOptions.setProfile(firefoxProfile);

        return firefoxOptions;
    }
    // 封装好的 chrome 浏览器驱动配置 设定方法
    protected static ChromeOptions setChromeDriverOptions(String downloadDirectory) {
        /* chrome 浏览器
         *     有关下载的，示例如下：
         *          <1> profile.default_content_settings.popups 默认值（数值） 1 ，设定弹出下载窗口，0 是禁止
         *          <2> download.default_directory 默认值 "<操作系统默认下载文件夹>"，设定自己指定的下载文件保存目录
         *
         *  */
        ChromeOptions chromeOptions = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadDirectory);
        chromeOptions.setExperimentalOption("prefs", chromePrefs);

        return chromeOptions;
    }


    /* 5：判断文件下载是否成功
     *         原文链接：https://blog.csdn.net/flower_other/article/details/113818119
     *
     *
     *  */









    /* X：在日期时间控件上进行选择
     *
     *  */
//    @Parameters("baseUrl3")
//    @Test
//    public void operateDatetimeWidget(String baseUrl3) throws InterruptedException {
//        driver.get(baseUrl3 + "/");
//
//        WebElement checkinTimeInputBox = driver.findElement(By.xpath("//*[@id=\"HD_CheckIn\"]"));
//        WebElement checkoutTimeInputBox = driver.findElement(By.xpath("//*[@id=\"HD_CheckOut\"]"));
//        checkinTimeInputBox.sendKeys("2021-04-31");
//        checkoutTimeInputBox.sendKeys("2021-05-31");
//
//        driver.findElement(By.xpath("//*[@id=\"HD_CityName\"]")).sendKeys("广州");
//        driver.findElement(By.xpath("//*[@id=\"HD_Btn\"]")).click();
//
//        Thread.sleep(15000);
//    }

}
