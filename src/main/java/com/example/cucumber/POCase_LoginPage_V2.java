package com.example.cucumber;

import com.epam.jdi.light.logger.AllureLogger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;

/*
 * 这是对 package com.example.selenwebdriver.pageobject.POCase_LoginPage 代码重构后得到的，重构时间 2021-05-14 17:30
 *
 * 使用目的：
 *     用于演示 "BDD + POM " ( 行为驱动测试 + Page Object 面向对象编程模式）高级 自动化测试框架
 *
 * 代码解释：
 *     自动化测试执行和管理模块 使用的是 JUnit ，而不是 TestNG
 *
 *  */
@RunWith(Parameterized.class)
public class POCase_LoginPage_V2 {
    // 打开一个 BDD 专用浏览器
    public static WebDriver driver = new BDD("chrome").getDriver();

    String userName = "";
    String password = "";
    String tips = "";

    public POCase_LoginPage_V2(String userName, String password, String tips) {
        this.userName = userName;
        this.password = password;
        this.tips = tips;
    }

    // 参数化
    @Parameterized.Parameters
    public static Collection<Object[]> preparedData() {
        Object[][] data = {
                {"x_man_no0", "TEST2021", "帐号或密码错误"},  // 注意：是"帐篷"的"帐"
                {"x_man_no1", "TEST2021", "收件箱"}
        };

        return Arrays.asList(data);
    }

    @BeforeClass
    public static void setUpAllure() {
//        // SelenideLogger 添加监听器 监听 AllureSelenide 日志
//        SelenideLogger.addListener("allure", new AllureSelenide());
        // LoggerFactory 类提供的 Logger 接口，来自 org.slf4j:slf4j-api:1.7.30
        Logger logger = LoggerFactory.getLogger(AllureLogger.class);
        logger.debug("allure");
        Logger loggerForDataProvider = LoggerFactory.getLogger(POCase_LoginPage_V2.class);
        loggerForDataProvider.debug("POCase_LoginPage_V2");
    }

    @AfterClass
    public static void tearDown() {
        try {
            // Nothing to do
        } finally {
            // 关闭浏览器
            driver.quit();
        }
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
    @Test
    public void testLogin() throws InterruptedException {
        // 定义一个 LoginPage 对象实例
        LoginPage_Copy_1 loginPage = new LoginPage_Copy_1(driver);
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
