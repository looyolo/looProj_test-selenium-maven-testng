package com.example.selenwebdriver.grid3;

import com.epam.jdi.light.logger.AllureLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;

public class Grid3Concurrency {
    WebDriver driver;

    @BeforeClass
    public static void setUpAllure() {
        // LoggerFactory 类提供的 Logger 接口，来自 org.slf4j:slf4j-api:1.7.30
        Logger logger = LoggerFactory.getLogger(AllureLogger.class);
        logger.debug("allure");
        Logger loggerForDataProvider = LoggerFactory.getLogger(Grid3Concurrency.class);
        loggerForDataProvider.debug("Grid3Concurrency");
    }

    @Parameters({"browser", "Url_Node"})
    @BeforeTest
    public void setUp_RemoteDriver(String browser, String Url_Node) throws MalformedURLException {
        if (browser.equalsIgnoreCase("firefox")) {
            // 访问 远程 Node 机器上的 操作系统 和 浏览器，需要设定 DesiredCapabilities 对象的属性
            //  DesiredCapabilities.firefox 设定 远程方法 要使用 firefox 浏览器
            DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
            // 设定 远程 Node 使用的浏览器为 firefox
            desiredCapabilities.setBrowserName(browser);
            // 设定 远程 Node 使用的操作系统为 LINUX
            desiredCapabilities.setPlatform(Platform.ANY);
            // 定义一个 RemoteWebDriver 对象，链接地址 使用 Url_Node1 变量，环境参数 使用 disiredCapabilityes 变量，并返回
            driver = new RemoteWebDriver(new URL(Url_Node), desiredCapabilities);
        } else if (browser.equalsIgnoreCase("chrome")) {
            DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
            desiredCapabilities.setBrowserName(browser);
            desiredCapabilities.setPlatform(Platform.WINDOWS);
            driver = new RemoteWebDriver(new URL(Url_Node), desiredCapabilities);
        } else if (browser.equalsIgnoreCase("internet explorer")) {
            DesiredCapabilities desiredCapabilities = DesiredCapabilities.internetExplorer();
            desiredCapabilities.setBrowserName(browser);
            desiredCapabilities.setPlatform(Platform.WINDOWS);
            driver = new RemoteWebDriver(new URL(Url_Node), desiredCapabilities);
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
     *     （5）通过 Grid3 远程方法 进行 "数据驱动 + 并发"测试
     *
     * 更多说明，前往查阅
     *      src/main/java/com/example/selenwebdriver/grid3/Jars_added_by_handcraft/Selenium Server (Grid 3)
     *
     * 该类 仅仅 作为 示例魔板，尚未 经过 实际测试
     *
     *  */
    @Test(dataProvider = "searchWords")
    public void testGrid3Concurrency(String searchWord1, String searchWord2, String searchResult) throws InterruptedException, MalformedURLException {
        driver.get("https://www.sogou.com/");

        driver.findElement(By.xpath("//*[@id=\"query\"]")).sendKeys(searchWord1 + " " + searchWord2);
        driver.findElement(By.xpath("//*[@id=\"stb\"]")).click();
        Thread.sleep(2000);
        Assert.assertTrue(driver.getPageSource().contains(searchResult));
    }

}
