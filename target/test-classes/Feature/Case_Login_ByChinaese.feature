# Comment: https://cucumber.io/tools/cucumber-open/
@simpleDemo
Feature: 登录 126邮箱

  Scenario: 登录 126邮箱 成功
    When 用户浏览 126邮箱 登录页面，网址为 “http://www.126.com”
    And 用户输入 用户名、密码后，点击登录
    Then 页面会跳转显示 ”收件箱“ 文案
  