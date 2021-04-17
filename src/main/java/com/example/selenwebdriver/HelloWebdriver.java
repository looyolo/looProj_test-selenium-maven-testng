package com.example.selenwebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import java.time.Duration;

public class HelloWebdriver {

    public static void main(String[] args) {
        // 指定 浏览器驱动程序 的 存放路径
        System.setProperty("webdriver.gecko.driver", "D:\\honor_selenium\\WebDriver_put_here\\geckodriver.exe");
        // 无头模式，提高性能，不会打开浏览器
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("-headless");

        WebDriver driver = new FirefoxDriver(firefoxOptions);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());

        try {
            driver.get("https://google.com/ncr");
            driver.findElement(By.name("q")).sendKeys("cheese" + Keys.ENTER);
            WebElement firstResult = wait.until(presenceOfElementLocated(By.cssSelector("h3")));
            System.out.println(firstResult.getAttribute("textContent"));
        } finally {
            driver.quit();
        }
    }
}
