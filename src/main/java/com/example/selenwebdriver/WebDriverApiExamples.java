package com.example.selenwebdriver;

import com.codeborne.selenide.Browser;
import com.codeborne.selenide.Browsers;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.epam.jdi.light.elements.common.Mouse;
import com.sun.java.swing.plaf.windows.WindowsGraphicsUtils;
import com.sun.javafx.logging.JFRInputEvent;
import io.qameta.allure.selenide.AllureSelenide;
import javafx.scene.input.MouseButton;

import org.apache.commons.io.FileUtils;
import org.apache.xpath.operations.Div;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Encodable;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.os.CommandLine;
import org.openqa.selenium.support.ui.Select;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * 代码解释：
 *   举例演示 WebDriver 常用 API 的使用方法
 */
public class WebDriverApiExamples {
    WebDriver driver;
    String baseUrl1, baseUrl2;

    // SelenideLogger 添加监听器 监听 AllureSelenide 日志
    @BeforeClass
    public static void setUpAllure() { SelenideLogger.addListener("allure", new AllureSelenide()); }

    @Parameters({"browser", "whichDriver", "whereDriver"})
    @BeforeClass
    public void setUp(String browser, String whichDriver, String whereDriver) {
        if (browser.equalsIgnoreCase("firefox")) {
            // 指定 浏览器驱动程序 的 存放路径，并添加为系统属性值
            System.setProperty(whichDriver, whereDriver);
            driver = new FirefoxDriver();
//            // 打开浏览器，使用"无头模式"，不会在桌面上打开，只在内存上运行
//            driver = new FirefoxDriver(new FirefoxOptions().addArguments("-headless"));
        }
        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty(whichDriver, whereDriver);
            driver = new ChromeDriver();
//            driver = new ChromeDriver(new ChromeOptions().addArguments("-headless"));
        }
    }

    @AfterClass
    public void tearDown() {
        try {
            // Nothing to do
        } finally {
            // 关闭浏览器
            driver.quit();
        }
    }

    /* 1: 访问某网页地址 */
    @Parameters("baseUrl1")
    @Test
    public void visitBaseUrl(String baseUrl1) {
        // 方法一
//        driver.get(baseUrl1 + "/");
        // 方法二
        driver.navigate().to(baseUrl1 + "/");
    }

    /* 2: 返回访问上一个网页，又前进到下一个网页,并刷新当前页面（模拟单击浏览器的 后退、前进、重新加载 功能）*/
    @Parameters({"baseUrl1", "baseUrl2"})
    @Test
    public void visitRecentUrl(String baseUrl1, String baseUrl2) {
        driver.navigate().to(baseUrl1);
        driver.navigate().to(baseUrl2);
        driver.navigate().back();
        driver.navigate().forward();
        driver.navigate().refresh();
    }

    /* 3: 操作浏览器窗口 */
    @Parameters("baseUrl1")
    @Test
    public void operateBrowserWindow(String baseUrl1) throws IOException {
        /* 情形 1 ：设定 浏览器 窗口的打开位置、打开窗口的大小
        *  */
        // 定义一个 Point 对象，两个 150 表示屏幕上位置相对于屏幕左上角 (0,0) 的横坐标距离、纵坐标距离
        Point point = new Point(150,150);
        // 定义一个 Dimension 对象，两个 500 表示屏幕上窗口的长度和宽度
        Dimension dimension = new Dimension(500,500);
        // 设定浏览器的位置为 point 对象的坐标，此方法对某些版本浏览器失效
        driver.manage().window().setPosition(point);
        // 设定浏览器窗口的大小为 dimension 对象的长和宽，此方法对某些版本浏览器失效
        driver.manage().window().setSize(dimension);
        // 获取浏览器在屏幕上的位置，此方法对某些版本浏览器失效
        System.out.println(driver.manage().window().getPosition());
        // 获取浏览器窗口的大小，此方法对某些版本浏览器失效
        System.out.println(driver.manage().window().getSize());
        // 设定浏览器窗口最大化
        driver.manage().window().maximize();
        driver.get(baseUrl1 + "/");

        /* 情形 2 ：对当前窗口截屏，保存截图文件到本地
        * */
        // 截屏，数据会存放在内存上
        File screenshotSrcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File screenshotDestFile = new File("C:\\Users\\Public\\Pictures\\selenium_screenshot_put_here\\screenshot_"
                // 截屏文件名，标明截屏的实际时间，时间格式不要包含空格、英文冒号等不合法字符
                + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())
                // 截屏文件扩展名，一般设定为 .png 或 .jpg
                + ".jpg");
        // 截屏数据，从内存上写入到本地磁盘
        FileUtils.copyFile(screenshotSrcFile, screenshotDestFile);
    }

    /* 4：获取页面的 Title 属性、页面源代码、当前页面的 URL 地址 等信息 */
    @Parameters("baseUrl1")
    @Test
    public void getPageInfo(String baseUrl1) {
        driver.get(baseUrl1 +"/");
        // 获取页面的 Title 属性，并断言它是不是"搜狗搜索引擎 - 上网从搜狗开始"
        String pageTitle = driver.getTitle();
        System.out.println("页面 Title 是 " + pageTitle);
        Assert.assertEquals("搜狗搜索引擎 - 上网从搜狗开始",pageTitle);
        // 获取页面的 源代码，并断言它是不是包含"购物"关键字
        String pageSource = driver.getPageSource();
        System.out.println("页面 源代码 是 " + pageSource);
        Assert.assertTrue(pageSource.contains("购物"));
        // 页面上点击"更多->购物"，进入购物主页
        driver.findElement(By.xpath("//*[@id=\"more-product\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"index_more_gouwu\"]")).click();
        // 获取页面 URL 地址，并断言它是否为"http://www.sogou.com"
        String currentUrl = driver.getCurrentUrl();
        System.out.println("页面 URL 是 " + currentUrl);
        Assert.assertEquals("http://www.sogou.com", currentUrl);
    }

    /* 5: 清除输入框中原有的内容，重新输入指定的内容 */
    @Parameters("baseUrl1")
    @Test
    public void operateInputBoxText(String baseUrl1) {
        driver.get(baseUrl1 + "/");
        WebElement inputBox = driver.findElement(By.xpath("//*[@id=\"query\"]"));
        // 清除输入框中原有的内容
        inputBox.clear();
        // 重新输入指定的内容
        String inputString = "指定的";
        inputBox.sendKeys(inputString, Keys.ENTER);

        // 代码段可选，主要用于等待 2 秒，让页面完成显示过程，查看操作后的结果
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* 6.1: 发送键盘组合键，模拟"全选 Ctrl+A ->复制 Ctrl+C ->粘贴 Ctrl+V"快捷键功能
    *         第 1 种方法: 通过 Actions 类实现
    * */
    @Parameters("baseUrl1")
    @Test
    public void ActionsCtrlACV(String baseUrl1) throws InterruptedException, AWTException {
        driver.get(baseUrl1 + "/");
        WebElement inputBox = driver.findElement(By.xpath("//*[@id=\"query\"]"));
        inputBox.clear();
        inputBox.sendKeys("搜狗");

        Thread.sleep(2000);
        // 定义一个 Actions 对象
        Actions action = new Actions(driver);
        // 模拟 全选 Ctrl+A
        action.keyDown(Keys.CONTROL).perform();  // 按下 Ctrl 键
        action.sendKeys(Keys.chord("A")).perform();  // 按下字母键，这里大小写均可
        action.keyUp(Keys.CONTROL).perform();  // 释放 Ctrl 键
        // 模拟 复制 Ctrl+C
        action.keyDown(Keys.CONTROL).perform();  // 按下 Ctrl 键
        action.sendKeys(Keys.chord("c")).perform();  // 按下字母键，这里大小写均可
        action.keyUp(Keys.CONTROL).perform();  // 释放 Ctrl 键

        Thread.sleep(2000);

        driver.navigate().to("http://www.baidu.com" + "/");
        inputBox = driver.findElement(By.xpath("//*[@id=\"kw\"]"));
        inputBox.clear();

        // 模拟 粘贴 Ctrl+V
        action.keyDown(Keys.CONTROL).perform();  // 按下 Ctrl 键
        action.sendKeys(Keys.chord("v")).perform();  // 按下字母键，这里大小写均可
        action.keyUp(Keys.CONTROL).perform();  // 释放 Ctrl 键

        Thread.sleep(2000);
    }

    /* 6.2: 发送键盘组合键，模拟"全选 Ctrl+A ->复制 Ctrl+C ->粘贴 Ctrl+V"快捷键功能
     *         第 2 种方法: 通过 Robot 类实现
     * */
    @Parameters("baseUrl1")
    @Test
    public void RobotCtrlACV(String baseUrl1) throws InterruptedException, AWTException {
        driver.get(baseUrl1 + "/");
        WebElement inputBox = driver.findElement(By.xpath("//*[@id=\"query\"]"));
        inputBox.clear();
        inputBox.sendKeys("搜狗");

        Thread.sleep(2000);
        // 定义一个 Robot 对象
        Robot robot = new Robot();
        // 模拟 全选 Ctrl+A
        robot.keyPress(KeyEvent.VK_CONTROL);  // 按下 Ctrl 键
        robot.keyPress(KeyEvent.VK_A);  // 按下字母键，这里大小写均可
        robot.keyRelease(KeyEvent.VK_A);  // 释放字母键，这里大小写均可
        robot.keyRelease(KeyEvent.VK_CONTROL);  // 释放 Ctrl 键
        // 模拟 复制 Ctrl+C
        robot.keyPress(KeyEvent.VK_CONTROL);  // 按下 Ctrl 键
        robot.keyPress(KeyEvent.VK_C);  // 按下字母键，这里大小写均可
        robot.keyRelease(KeyEvent.VK_C);  // 释放字母键，这里大小写均可
        robot.keyRelease(KeyEvent.VK_CONTROL);  // 释放 Ctrl 键

        Thread.sleep(2000);

        driver.navigate().to("http://www.baidu.com" + "/");
        inputBox = driver.findElement(By.xpath("//*[@id=\"kw\"]"));
        inputBox.clear();

        // 模拟 粘贴 Ctrl+V
        robot.keyPress(KeyEvent.VK_CONTROL);  // 按下 Ctrl 键
        robot.keyPress(KeyEvent.VK_V);  // 按下字母键，这里大小写均可
        robot.keyRelease(KeyEvent.VK_V);  // 释放字母键，这里大小写均可
        robot.keyRelease(KeyEvent.VK_CONTROL);  // 释放 Ctrl 键

        Thread.sleep(2000);
    }

    /* 7: 操作下拉列表
    *         selenium 中有一个 Select 类，用来操作 select 下拉列表
    * */
    @Parameters("baseUrl1")
    @Test
    public void operateDropList(String baseUrl1) throws InterruptedException {
        driver.get(baseUrl1 + "/");
        Thread.sleep(2000);

        // 鼠标移动到"搜狗搜索"主页右上角，悬停在"天气"位置，不做任何点击操作
        WebElement aboutWeather = driver.findElement(By.xpath("//*[@id=\"cur-weather\"]"));
        Actions action = new Actions(driver);
        action.moveToElement(aboutWeather).perform();

        /*
        *  这里是一个非 select 下拉列表，
        *   操作情形与网页元素操作一样：定位元素，设置等待，点击元素等等
        * */
        //  鼠标点击"更换城市"，可以看到"省份"下拉列表
        WebElement changeCity = driver.findElement(By.xpath("//*[@id=\"weather-city-btn\"]"));
        changeCity.click();

        /*
        *  重点：
        *     Select 类 操作 select 下拉列表，实现 单选
        * */
        WebElement aboutProvince = driver.findElement(By.xpath("//*[@id=\"weather-province\"]"));
        Select dropListAboutProvince = new Select(aboutProvince);
        // 断言是否多选，isMultiple 判断这个下拉列表是否允许多选。标签 Select 包含属性 multiple=true 时，表示允许多选，否则不允许
        Assert.assertFalse(dropListAboutProvince.isMultiple());
        // 断言当前被选中的选项中文本内容是否为"广东"， getFirstSelectedOption().getText() 返回当前被选中的选项中文本内容
        Assert.assertTrue(dropListAboutProvince.getFirstSelectedOption().getText().equals("广东"));
        // 断言当前被选中的选项中文本内容是否为"广东"， selectByIndex 表示通过走索引选中，索引 0 表示第一选项，以此类推
        dropListAboutProvince.selectByIndex(8);
        Assert.assertEquals(dropListAboutProvince.getFirstSelectedOption().getText(),"广东");
        // 断言当前被选中的选项中文本内容是否为"广东"，selectByValue 表示通过属性 value 值匹配选中
        dropListAboutProvince.selectByValue("广东");
        Assert.assertEquals(dropListAboutProvince.getFirstSelectedOption().getText(),"广东");
        // 断言当前被选中的选项中文本内容是否为"广东"， selectByVisibleText 表示通过选项中文本匹配选中
        dropListAboutProvince.selectByVisibleText("广东");
        Assert.assertEquals(dropListAboutProvince.getFirstSelectedOption().getText(),"广东");

        /*
         * 经典场景用例：
         *   检查 单选列表的选项文字是否符合期望的设计意图
         *   定义 2 个 List 对象，通过泛型 <String> 限定为 String 类型，
         *    分别存储 页面上实际选项 expected_options 、设计意图中期望选项 expected_options ，
         *     遍历逐一断言比对
         */
        // 设计意图中期望出现的全部选项的文案，存到 List<String> 对象 expected_options 中
        String[] expected_info = {"本地天气", "北京", "上海", "天津", "重庆", "安徽", "福建", "甘肃", "广东", "广西",
                "贵州", "海南", "河北", "河南", "黑龙江", "湖北", "湖南", "吉林", "江苏", "江西", "辽宁", "内蒙古",
                "宁夏", "青海", "山东", "山西", "陕西", "四川", "西藏", "新疆", "云南", "浙江", "香港", "澳门", "台湾"};
        List<String> expected_options = Arrays.asList(expected_info);
        // 实际功能设计完成后页面上实际选项的文案，存到 List<String> 对象 actual_options 中
        List<String> actual_options = new ArrayList<String>();
        for (WebElement option : dropListAboutProvince.getOptions()) actual_options.add(option.getText());
        // 遍历逐一断言比对
        for (int i = 0; i < expected_options.size(); i++) {
            Assert.assertEquals(actual_options.get(i), expected_options.get(i));
            int j = i + 1 ;
            System.out.println("断言比对次数：" + j);
        }

        /*
         *  备忘：
         *     Select 类 操作 select 下拉列表，实现 多选
         *         操作情形与实现单选时一样：定位到下拉列表元素，生成 Select 对象，判断是否允许多选，多处单选等等
         *             有一点区别在于，多选时，deselectBy... 用于取消选项的选中状态
         *         多选的应用情形并不多见，这里暂时找不到合适的网站来演示
         *         多选，多见于"选择框（打勾，或变高亮）"的使用
         * */
    }

    /* 7.1: 操作单选框
     *         单选框 对应到标签 input 属性 type="ratio"
     * */
    @Parameters("baseUrl4")
    @Test
    public void operateRatio(String baseUrl4) throws InterruptedException {
        driver.get(baseUrl4 + "/");

        // 查找（是 findElements 不是 findElement ）属性 type="ratio" 与 name="second" 的所有 input 标签，存入 List<WebElement> 容器中
        List<WebElement> ratio_options = driver.findElements(By.xpath("//child::input[@type=\"radio\" and @name=\"second\"]"));
        System.out.println("ratio_options 个数为 " + ratio_options.size());

        // for 循环 遍历 List<WebElement> 容器中所有选框
        for (WebElement ratio_option : ratio_options) {
            // 查找 属性 id="sencond_appoint" 的单选框，若未被选中，则调用 click 单击选中它，并断言是否选中，选中则直接退出整个循环
            if (ratio_option.getAttribute("id").equals("sencond_appoint")) {
                if (!ratio_option.isSelected()) {
                    ratio_option.click();
                    Assert.assertTrue(ratio_option.isSelected());

                    break;
                }
            }
        }
        Thread.sleep(2000);
    }

    /* 7.2: 操作复选框
     *         复选框 对应到标签 input 属性 type="checkbox"
     * */
    @Parameters("baseUrl4")
    @Test
    public void operateCheckBox(String baseUrl) throws InterruptedException {
        driver.get(baseUrl + "/");

        // 查找（是 findElements 不是 findElement ）属性 type="checkbox" 的所有 input 标签，存入 List<WebElement> 容器中
        List<WebElement> checkbox_options = driver.findElements(By.xpath("//child::div[@class=\"imp secondList\"]/input[@type=\"checkbox\"]"));
        System.out.println("checkbox_options 个数是 " + checkbox_options.size());

        // for 循环 遍历 List<WebElement> 容器中所有选框
        for (WebElement checkbox_option : checkbox_options) {
            // 正则陪匹配出属性 value 值在 65 以下个位数为 5 的两位数选项
            if (checkbox_option.getAttribute("value").matches("[0-6]?5")) {
                // 若复选框未被选中，则调用 click 单击选中它，并断言是否选中
                if (!checkbox_option.isSelected()) {
                    checkbox_option.click();
                    Assert.assertTrue(checkbox_option.isSelected());
                }
            }
        }
        Thread.sleep(2000);

        // for 循环 遍历 List<WebElement> 容器中所有选框
        for (WebElement checkbox_option : checkbox_options) {
            // 正则陪匹配出属性 value 值在 65 以下个位数为 5 的两位数选项
            if (checkbox_option.getAttribute("value").matches("[0-6]?5")) {
                // 若复选框已经被选中，则调用 click 单击取消选中它，并断言是否取消选中
                if (checkbox_option.isSelected()) {
                    checkbox_option.click();
                    Assert.assertTrue(!checkbox_option.isSelected());
                }
            }
        }
        Thread.sleep(2000);
    }

    /* 8：杀掉 Windowss 浏览器 进程
    *         需要从 https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-remote-driver
    *         添加 maven 依赖导入 WindowsUtils 类，亲测这个类还有存在于 Version 3.14.0
    *         不过，已经标记为过时，不赞成使用。Aug, 2018 之后就没有这个类了。
    *         但是，查看源码，了解逻辑后，是可以通过 CommandLine 类来实现同样的目的和作用的。
    * */
    @Parameters("processName")
    @Test
    public void killBrowserProcess(String processName) throws InterruptedException {
        // 判断当前系统平台是否为 WINDOWS ，也可以是 UNIX、LINUX、IOS 等等
        if (Platform.getCurrent().is(Platform.WINDOWS)) {
            System.out.println("当前系统平台：" + Platform.WINDOWS);
            // 杀掉 Windows 进程中的 IE 浏览器集成，关闭所有 IE 浏览器
            CommandLine windowsCommandLine = new CommandLine("taskkill", "/f", "/t", "/pid", processName);
            // 指定 命令行执行结果打印 输出到 IDEA output console，必须在调用 execute 之前进行指定
            windowsCommandLine.copyOutputTo(System.out);
            // 执行命令
            windowsCommandLine.execute();
        }
    }

    /* 9: 操作鼠标
    *        主要的：鼠标缩放，鼠标滚动，执行 Javascript 脚本
    *        其他：  切换到 iframe ，从 iframe 切换为 主界面
    *  */
    @Parameters("demoUrl1")
    @Test
    public void operateMouse(String demoUrl1) throws InterruptedException {
        driver.get(demoUrl1);
        driver.manage().window().maximize();

        /* 切换到 iframe
        *     将被拖放的元素 就在 iframe 里面，需要先切换进去，才能定位到，这里要注意先后次序
        * */
        WebElement iframe = driver.findElement(By.xpath("//*[@id=\"content\"]/iframe"));
        driver.switchTo().frame(iframe);
        WebElement draggable = driver.findElement(By.xpath("//*[@id=\"draggable\"]"));

        /* 鼠标 缩放，滚动到使得 将被拖放的目标元素 全部 清晰可见
        *     定义一个 JavaScript 执行器对象，调用 executeScript 执行 Javascript 脚本实现
        *     不要使用 javascriptExecutor.executeScript("document.body.style.zoom='倍数小数，如 1.2'");
        * */
        JavascriptExecutor javascriptExecutor = ((JavascriptExecutor)driver);
        javascriptExecutor.executeScript("document.body.style.transform='scale(1.5)';");  // 放大到 150%
        javascriptExecutor.executeScript("arguments[0].scrollIntoView();", draggable);  // 滚动页面

        /* 拖拽页面元素
        *  */
        Actions action = new Actions(driver);
        for (int i = 0; i < 10; i++) {
            action.dragAndDropBy(draggable, 10,0).perform();
        }

        // 增加一些动态效果示例
        javascriptExecutor.executeScript("document.body.style.transform='scale(0.8)';");  // 缩小到 80%
        javascriptExecutor.executeScript("arguments[0].scrollIntoView();", draggable);  // 滚动页面
        for (int i = 0; i < 10; i++) {
            action.dragAndDropBy(draggable, 0,10).perform();
        }

        System.out.println((String) javascriptExecutor.executeScript(
                "return document.title;"));

        /* 从 iframe 切换为 主界面
        *  */
        driver.switchTo().defaultContent();

        Thread.sleep(2000);
    }





}
