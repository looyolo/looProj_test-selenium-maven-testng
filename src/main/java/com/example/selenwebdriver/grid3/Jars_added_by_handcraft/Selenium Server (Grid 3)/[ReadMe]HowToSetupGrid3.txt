
【1】















【2】怎么搭建一个自己的 Selenium Server Grid 3 环境？

参阅官方文档 https://www.selenium.dev/documentation/en/grid/grid_3/setting_up_your_own_grid/
其中，重点关注 Docker Selenium

方法/步骤：

（1）准备好 机器 A、B，分别在指定目录下放好 selenium-server-standalone.jar 包

（2）进入到机器 A 的 jar 包所在目录，打开命令行窗口，启动一个 Hub 机器 的 java 进程：

java -jar selenium-server-standalone-3.141.59.jar -role hub -hubConfig Config_hub.json -debug -log ./logs/grid3.log

（3）访问网址 http://localhost:4444/grid/console

验证 网页上是否显示"View Config"的链接，即可判断 Hub 启动 是否 成功

（4）进入到机器 B 的 jar 包所在目录，打开命令行窗口，启动一个 Node 机器 的 java 进程：

java -Dwebdriver.chrome.driver=chromedriver.exe -jar selenium-server-standalone-3.141.59.jar -role node -nodeConfig Config_node1.json

（5）刷新，再次访问网址 http://localhost:4444/grid/console

点击"View Config"的链接，验证 打开的网页上是否显示"Browsers"，即可判断 Node 在 Hub 注册 是否 成功。

另外，在此页面中，可以获取 Node 机器（即 机器 B ）允许不同种类的浏览器 打开多少个实例，验证 Node 机器 执行命令行的正确性。


参数说明：

-port  指定 Node 机器提供远程连接服务的端口号，注意：port 使用大于 5000 的 端口号
-timeout  指定 Hub 机器 在 无法收到 Node 机器的任何请求后，等待 timeout 后自动释放和 Node 机器的连接。
            注意：此参数不是 WebDriver 定位页面元素的最大等待时间。
-maxSessionn  指定一台 Node 机器 允许同时最多打开多少个浏览器窗口
-browser  指定 Node 机器 允许使用的浏览器信息，如下：
            version  指定浏览器版本号，当多个版本号共存时，要明确使用哪一个版本号进行测试
            platform  指定 Node 机器的操作系统属性，常用的有 WINDOWS / lINUX / MAC / ANDROID / ANY 
            firefox_binary  指定 firefox 浏览器启动路径
            maxInstances  指定 最多允许同时启动多少个浏览器窗口
-registerCycle  指定 Node 机器间隔多少毫秒（ms)注册一下 Hub 机器的 console 管理中心


