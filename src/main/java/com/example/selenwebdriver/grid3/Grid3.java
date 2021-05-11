package com.example.selenwebdriver.grid3;

import com.epam.jdi.light.logger.AllureLogger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Grid3 {
    WebDriver driver;
    // 定义 远程 Node 的 URL 字符串，后续通过 访问此地址 连接到这个 Node 机器
    public String Url_Node1 = "http://{hostname or ip}:{port}/wd/hub";
    public String Url_Node2 = "http://{hostname or ip}:{port}/wd/hub";
    public String Url_Node3 = "http://{hostname or ip}:{port}/wd/hub";

    @BeforeClass
    public static void setUpAllure() {
        // LoggerFactory 类提供的 Logger 接口，来自 org.slf4j:slf4j-api:1.7.30
        Logger logger = LoggerFactory.getLogger(AllureLogger.class);
        logger.debug("allure");
        Logger loggerForDataProvider = LoggerFactory.getLogger(Grid3.class);
        loggerForDataProvider.debug("Grid3");
    }

    @Parameters("browser")
    @BeforeClass
    public void setUp_RemoteDriver(String browser) throws MalformedURLException {
        // 访问 远程 Node 机器上的 操作系统 和 浏览器，需要设定 DesiredCapabilities 对象的属性
        DesiredCapabilities desiredCapabilities;
        if (browser.equalsIgnoreCase("firefox")) {
            // DesiredCapabilities.firefox 设定 远程方法 要使用 firefox 浏览器
            desiredCapabilities = DesiredCapabilities.firefox();
            // 设定 远程 Node 使用的浏览器为 firefox 
            desiredCapabilities.setBrowserName("firefox");
            // 设定 远程 Node 使用的操作系统为 LINUX
            desiredCapabilities.setPlatform(Platform.LINUX);
            // 定义一个 RemoteWebDriver 对象，链接地址 使用 Url_Node1 变量，环境参数 使用 disiredCapabilityes 变量
            driver = new RemoteWebDriver(new URL(Url_Node1), desiredCapabilities);
        }
        if (browser.equalsIgnoreCase("chrome")) {
            desiredCapabilities = DesiredCapabilities.chrome();
            desiredCapabilities.setBrowserName("chrome");
            desiredCapabilities.setPlatform(Platform.WINDOWS);
            driver = new RemoteWebDriver(new URL(Url_Node2), desiredCapabilities);
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
     *     （5）通过 Grid3 远程方法 进行 "数据驱动"测试
     *     （6）在远程 Node 机器上 进行截屏
     *
     * 更多说明，前往查阅
     *      src/main/java/com/example/selenwebdriver/grid3/Jars_added_by_handcraft/Selenium Server (Grid 3)
     *
     * 该类 仅仅 作为 示例魔板，尚未 经过 实际测试
     *
     *  */
    @Test(dataProvider = "searchWords")
    public void testGrid3(String searchWord1, String searchWord2, String searchResult) throws InterruptedException, IOException {
        driver.get("https://www.sogou.com/");

        driver.findElement(By.xpath("//*[@id=\"query\"]")).sendKeys(searchWord1 + " " + searchWord2);
        driver.findElement(By.xpath("//*[@id=\"stb\"]")).click();
        Thread.sleep(2000);
        // 只在远程 Node 机器上截图时，添加这一行代码，否则会截图失败
        driver = new Augmenter().augment(driver);
        File screenshotFilesrc = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFilesrc, new File("C:\\node_screenshot.png"));
        Assert.assertTrue(driver.getPageSource().contains(searchResult));
    }

}
