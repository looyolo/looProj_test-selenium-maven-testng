package com.example.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/*
* 代码解释：
*     测试运行类，即测试执行的入口类
*         @RunWith  指定运行测试用例要运用到的测试框架类 Cucumber.class ，
*         @CucumberOptions  设定 Cucumber 配置项：
*           - features  指定存放剧本文件的位置
*           - tags  指定运行时，只执行打上 tag 的用例
*           - glue  指定测试代码所在的 Package 名称
*           - plugin  指定运行后，测试结果会在项目工程根目录下，指定路径，生成 html 报告或 json 文件。
*                      指定路径可自行修改，会自动创建不存在的目录。
*
*  */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/Feature",
        tags = "@simpleDemo",
        glue = {"com.example.cucumber"},
        plugin = {"pretty","html:cucumber-result/cucumber.html","json:cucumber-result/cucumber.json"})
public class TestRunnerForCucumber {
}