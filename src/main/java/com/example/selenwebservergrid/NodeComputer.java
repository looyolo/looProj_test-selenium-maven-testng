package com.example.selenwebservergrid;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class NodeComputer {

    public static RemoteWebDriver getFirefoxRemoteDriver(String browser, String Url_Node) throws MalformedURLException {
        // 访问 远程 Node 机器上的 操作系统 和 浏览器，需要设定 DesiredCapabilities 对象的属性
        //  DesiredCapabilities.firefox 设定 远程方法 要使用 firefox 浏览器
        DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
        // 设定 远程 Node 使用的浏览器为 firefox
        desiredCapabilities.setBrowserName(browser);
        // 设定 远程 Node 使用的操作系统为 LINUX
        desiredCapabilities.setPlatform(Platform.ANY);
        // 定义一个 RemoteWebDriver 对象，链接地址 使用 Url_Node1 变量，环境参数 使用 disiredCapabilityes 变量，并返回

        return new RemoteWebDriver(new URL(Url_Node), desiredCapabilities);
    }

    public static RemoteWebDriver getChromeRemoteDriver(String browser, String Url_Node) throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
        desiredCapabilities.setBrowserName(browser);
        desiredCapabilities.setPlatform(Platform.WINDOWS);

        return new RemoteWebDriver(new URL(Url_Node), desiredCapabilities);
    }

    public static RemoteWebDriver getIERemoteDriver(String browser, String Url_Node) throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = DesiredCapabilities.internetExplorer();
        desiredCapabilities.setBrowserName(browser);
        desiredCapabilities.setPlatform(Platform.WINDOWS);

        return new RemoteWebDriver(new URL(Url_Node), desiredCapabilities);
    }

}
