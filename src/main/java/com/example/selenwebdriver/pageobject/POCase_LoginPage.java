package com.example.selenwebdriver.pageobject;

import com.epam.jdi.light.logger.AllureLogger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

public class POCase_LoginPage {
    WebDriver driver;

    @BeforeClass
    public static void setUpAllure() {
//        // SelenideLogger 添加监听器 监听 AllureSelenide 日志
//        SelenideLogger.addListener("allure", new AllureSelenide());
        // LoggerFactory 类提供的 Logger 接口，来自 org.slf4j:slf4j-api:1.7.30
        Logger logger = LoggerFactory.getLogger(AllureLogger.class);
        logger.debug("allure");
        Logger loggerForDataProvider = LoggerFactory.getLogger(POCase_LoginPage.class);
        loggerForDataProvider.debug("POCase_LoginPage");
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

    @DataProvider
    public static Object[][] userInfo() {
        return new Object[][]{
                {"x_man_no0", "TEST2021", "帐号或密码错误"},  // 注意：是"帐篷"的"帐"
                {"x_man_no1", "TEST2021", "收件箱"}
        };
    }

    /*
     * 测试逻辑：
     *     （1）定义一个"登录"页面对象，并准备好测试数据(用户名、密码、提示词)，同时实现 "数据驱动"
     *     （2）打开"126邮箱"登录页
     *     （3）进入到登录窗口(这里是一个 iframe )页面中
     *     （4）输入用户名、密码后，点击‘登录’按钮，停顿 2 秒，等待页面加载完成
     *     （5）验证页面源代码是否包含期望的提示词，包含则认为测试执行成功，否则失败
     *     （6）退出登录窗口，返回到默认页面
     *
     *  */
    @Test(dataProvider = "userInfo")
    public void testLogin(String userName, String password, String tips) throws InterruptedException {
        // 定义一个 LoginPage 对象实例
        LoginPage loginPage = new LoginPage(driver);
        //  准备好的 测试数据(用户名、密码、提示词)，进行 数据驱动测试
        loginPage.preparedUserInfo(userName, password, tips);
        // 调用 登录页面对象的 load 方法 去访问被测试网址
        loginPage.load();
        // 调用 登录页面对象的 switchFrame 方法 进入 frame
        loginPage.switchFrame();
        // 调用 登录页面对象的 login 方法 进行登录
        loginPage.login();
        // 停顿 2 秒，等待页面加载完成
        Thread.sleep(2000);
        // 调用 登录页面对象的 assert 方法 断言 页面源代码是否包含期望的提示词
        loginPage.allege();
        // 调用 登录页面对象的 defaultFrame 方法 退出 frame 并返回到默认页面
        loginPage.defaultFrame();
    }

}
