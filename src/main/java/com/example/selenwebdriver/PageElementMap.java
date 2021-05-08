package com.example.selenwebdriver;

import com.epam.jdi.light.common.Exceptions;
import org.openqa.selenium.By;

import java.io.*;
import java.util.Properties;

/* 封装好的 工具类 */
public class PageElementMap {
    protected Properties properties = new Properties();
    public PageElementMap(String propertiesFilePath) throws IOException {
        /*
        * 读取 Properties 配置文件
        *  文件路径可以是 相对路径、绝对路径
        *  */
        // 知道了 propertiesFilePath 文件路径，读取它之前，要先定义一个 FileInputStream 文件输入流对象，层级：低级，字节流
        FileInputStream fileInputStream = new FileInputStream(propertiesFilePath);
        // 有了 FileInputStream 之后，要定义一个 Reader 输入流读取器对象，从文件输入流中读取数据，层级：中级，字符流
        Reader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8") ;
        // Properties 对象 加载 inputStreamReader 读取到的数据，也就是 页面元素的定位表达式 等配置信息
        properties.load(inputStreamReader);
        // 加载完成配置信息后，一定要记得 关闭 输入流读取器
        inputStreamReader.close();
    }

    /*
    * 通过 页面元素名称 从 PageElementMap 对象地图 的配置文件中 获取对应的 locator 定位信息字符串
    * */
    public By getLocator(String webElementNameInPropertiesFile) throws Exception {
        String locator = properties.getProperty(webElementNameInPropertiesFile);
        // 解析 locator 定位信息字符串，得到 定位方式、定位要素 等信息
        String locatorMethod = locator.split(">")[0];
        String locatorFactor = locator.split(">")[1].replace(" ","");  // 去除"空格"字符
        System.out.println("解析得知：定位方式【" + locatorMethod + "】，定位要素【" + locatorFactor + "】");

        /*
        * 逐一判断 符合哪一种定位方式，返回相符合的包含 定位要素 的 By 定位器对象，常见的已定义的有 8 种定位方式
        * */
        if (locatorMethod.equalsIgnoreCase("id")) {  // id 定位方式
            return By.id(locatorFactor);
        } else if (locatorMethod.equalsIgnoreCase("name")) {  // name 定位方式
            return By.name(locatorFactor);
        } else if (locatorMethod.equalsIgnoreCase("xpath")) {  // xpath 定位方式
            return By.xpath(locatorFactor);
        } else if (locatorMethod.equalsIgnoreCase("partialLinkText")) {  // partialLinkText 定位方式
            return By.partialLinkText(locatorFactor);
        } else if (locatorMethod.equalsIgnoreCase("linkText")) {  // linkText 定位方式
            return By.linkText(locatorFactor);
        } else if (locatorMethod.equalsIgnoreCase("cssSelector")) {  // cssSelector 定位方式
            return By.cssSelector(locatorFactor);
        } else if (locatorMethod.equalsIgnoreCase("className")) {  // className 定位方式
            return By.className(locatorFactor);
        } else if (locatorMethod.equalsIgnoreCase("tagName")) {  // tagName 定位方式
            return By.tagName(locatorFactor);
        } else throw new Exception("出现未经定义的定位方式，可能是 JQuery 定位方式，请检查配置文件！");
    }
}
