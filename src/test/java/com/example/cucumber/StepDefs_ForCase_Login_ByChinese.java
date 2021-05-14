package com.example.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

/*
 * 代码解释：
 *     演示 "BDD + POM " ( 行为驱动测试 + Page Object 面向对象编程模式）高级 自动化测试框架
 *     自动化测试基础功能模块 使用的是 Gherkin 语言 特定的 关键字 来注入解释 Behavior 用户行为
 *
 *  */
public class StepDefs_ForCase_Login_ByChinese {
    // 打开一个 BDD 专用浏览器
    public static WebDriver driver = new BDD("chrome").getDriver();

    String userName = "x_man_no1";
    String password = "TEST2021";
    String tips = "收件箱";

    // 定义一个 LoginPage 对象实例
    LoginPage_Copy_1 loginPage = new LoginPage_Copy_1(driver);

    /* 重点：
    *      实现 将 自然语言中 步骤的内容 传递给 测试类的函数，满足一些 复杂场景 的 测试需求
    *         方法：
    *             使用 正则表达式 从自然语言中 提取数值或字符串，作为对应的 StepDefs 类方法的 传入参数值，再传递到 测试类的函数
    *         注意：
    *             （1）StepDefs 类方法的注解中自然语言 必须以 ^ 开始，以 $ 结束，即 形如 “^自然语言$”
    *             （2）StepDefs 类方法的 传入参数值 时，必须 实现 throws Throwable 接口
     *
    *  */
    @When("^用户浏览 (\\d+)邮箱 登录页面，网址为 “([a-zA-z]+[://]+[^\\s\"“”]*)”$")
    public void 用户浏览邮箱登录页面网址为(int arg0, String arg1) throws Throwable {
        //  准备好的 测试数据(用户名、密码、提示词)，进行 数据驱动测试
        loginPage.preparedUserInfo(userName, password, tips);
        // 调用 登录页面对象的 load 方法 去访问被测试网址
        loginPage.load();
        // 调用 登录页面对象的 switchFrame 方法 进入 frame
        loginPage.switchFrame();

        //  打印 正则表达式 提取的内容 到 控制台 进行查看
        System.out.println("访问 " + arg0 + "邮箱，网页网址： " + arg1);
    }

    @And("用户输入 用户名、密码后，点击登录")
    public void 用户输入用户名密码后点击登录() throws InterruptedException {
        // 调用 登录页面对象的 login 方法 进行登录
        loginPage.login();
        // 停顿 2 秒，等待页面加载完成
        Thread.sleep(2000);
        // 调用 登录页面对象的 assert 方法 断言 页面源代码是否包含期望的提示词
        loginPage.allege();
    }

    @Then("页面会跳转显示 ”收件箱“ 文案")
    public void 页面会跳转显示收件箱文案() {
        // 调用 登录页面对象的 defaultFrame 方法 退出 frame 并返回到默认页面
        loginPage.defaultFrame();

        try {
            // Nothing to do
        } finally {
            // 关闭浏览器
            driver.quit();
        }
    }

}
