package com.example.cucumber;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BDD {
    WebDriver driver;

    public BDD(String browser) {
        if (browser.equalsIgnoreCase("firefox")) {
            // 指定 浏览器驱动程序 的 存放路径，并添加到系统属性中
            System.setProperty("webdriver.gecko.driver", "D:\\honor_selenium\\WebDriver_put_here\\geckodriver.exe");
            driver = new FirefoxDriver();
            // 打开浏览器，使用"无头模式"，不会在桌面上打开，只在内存上运行
//            driver = new FirefoxDriver(new FirefoxOptions().addArguments("-headless"));
        }
        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "D:\\honor_selenium\\WebDriver_put_here\\chromedriver.exe");
            driver = new ChromeDriver();
//            driver = new ChromeDriver(new ChromeOptions().addArguments("-headless"));
        }
    }

    public WebDriver getDriver() {
        return this.driver;
    }

}
