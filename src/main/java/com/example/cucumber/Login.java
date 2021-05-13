package com.example.cucumber;

import com.epam.jdi.light.logger.AllureLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Login {
    WebDriver driver;

    @BeforeClass
    public static void setUpAllure() {
//        // SelenideLogger 添加监听器 监听 AllureSelenide 日志
//        SelenideLogger.addListener("allure", new AllureSelenide());
        // LoggerFactory 类提供的 Logger 接口，来自 org.slf4j:slf4j-api:1.7.30
        Logger logger = LoggerFactory.getLogger(AllureLogger.class);
        logger.debug("allure");
        Logger loggerForDataProvider = LoggerFactory.getLogger(Login.class);
        loggerForDataProvider.debug("Login");
    }

    @Parameters({"browser", "whichDriver" , "whereDriver"})
    @BeforeClass
    public void setUp(String browser, String whichDriver , String whereDriver) {
        if (browser.equalsIgnoreCase("firefox")) {
            // 指定 浏览器驱动程序 的 存放路径，并添加到系统属性中
            System.setProperty(whichDriver, whereDriver);
            driver = new FirefoxDriver();
            // 打开浏览器，使用"无头模式"，不会在桌面上打开，只在内存上运行
//            driver = new FirefoxDriver(new FirefoxOptions().addArguments("-headless"));
        }
        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty(whichDriver, whereDriver);
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

    @org.testng.annotations.DataProvider
    public static Object[][] searchWords() {
        return new Object[][]{
                {"蝙蝠侠", "主演", "迈克尔"},
                {"超人", "导演", "唐纳"},
                {"生化危机", "编剧", "安德森"}
        };
    }

    /*
    * 测试逻辑：
    *     （1）打开搜狗首页
    *     （2）在 搜索输入框 输入 2 个关键词
    *     （3）点击 搜索按钮，等待 2 秒，让页面加载完成
    *     （4）验证搜索结果页面是否包含输入的每行第三个中文词，包含则认为测试执行成功，否则失败
    *
    * 注意：
    *     @Parameters("baseUrl1") 与 @Test(dataProvider = "searchWords") 不允许同时对 testDataProvider 使用
    *
    * 代码解释：
    *     测试脚本会自动依次打开三次浏览器，分别输入三组不同的关键词进行搜索，并且三次搜索结果均可断言成功。
    *     其中，使用 @Login 注解将当前方法中的返回对象作为测试脚本的 测试数据集，取名为"searchWords"。
    *         取名可以在 2 个地方，在 @org.testng.annotations.Login(name = "searchWords") 或 当前方法名。
    *             至少得有一个地方的 取名 和 @Test(dataProvider = "searchWords") 中的保持一致，才可以读取到测试数据集。
    *
    *  */
    @Test(dataProvider = "searchWords")
    public void testDataProvider(String searchWord1, String searchWord2, String searchResult) throws InterruptedException {
        driver.get("https://www.sogou.com/");

        driver.findElement(By.xpath("//*[@id=\"query\"]")).sendKeys(searchWord1 + " " + searchWord2);
        driver.findElement(By.xpath("//*[@id=\"stb\"]")).click();
        Thread.sleep(2000);
        Assert.assertTrue(driver.getPageSource().contains(searchResult));
    }

}
