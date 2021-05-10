package com.example.selenwebdriver.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

/*
* 代码解释：
*     在 LoginPage 页面对象中，不仅从测试脚本中分离出 页面元素的定位表达式（使用 @FindBy 注解），
*     还封装了 页面元素的操作方法，使得在测试脚本中实现测试逻辑更加清晰容易。
*     这些封装好的 页面元素的操作方法 还可以被很多测试逻辑重复调用，从而调高了脚本编写和维护的效率。
*     如果将来测试过程中的元素定位发生了变化，或者页面的某个操作过程发生了变化，
*     仅仅修改封装好的测试方法即可实现维护。
*
*  */
public class LoginPage {
    WebDriver driver;
    String url = "https://mail.126.com/";

    String userName = "";
    String password = "";
    String tips = "";

    @FindBy(xpath = "//*[@id=\"normalLoginTab\"]//iframe")  // 注意：这里 iframe id 是随机生成的，无法直接使用 id 定位到
    WebElement iframe;
    @FindBy(xpath = "//*//input[@data-placeholder=\"邮箱帐号或手机号码\"]")
    WebElement userNameInputBox;
    @FindBy(xpath = "//*//input[@data-placeholder=\"输入密码\"]")
    WebElement passwordInputBox;
    @FindBy(xpath = "//*[@id=\"dologin\"]")
    WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver,this);
    }

    public void preparedUserInfo(String userName, String password, String tips) {
        this.userName = userName;
        this.password = password;
        this.tips = tips;
    }

    public void load() {
        driver.get(url);
    }

    public void switchFrame() {
        driver.switchTo().frame(iframe);
    }

    public void login() {
        userNameInputBox.sendKeys(userName);
        passwordInputBox.sendKeys(password);
        loginButton.click();
    }

    public void allege() {
        Assert.assertTrue(driver.getPageSource().contains(tips));
    }

    public void defaultFrame() {
        driver.switchTo().defaultContent();
    }

}
