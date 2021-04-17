package com.example.selenide;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class MainPage_Sogou {
    public SelenideElement sogou_Search = $x("//*[@id=\"wrap\"]//span[text()='网页']");
    public SelenideElement sogou_Wechat = $x("//*[@id=\"weixinch\"]");
    public SelenideElement sogou_Zhihu = $x("//*[@id=\"zhihu\"]");
    public SelenideElement sogou_Picture = $x("//*[@id=\"pic\"]");
    public SelenideElement sogou_Video = $x("//*[@id=\"video\"]");
    public SelenideElement sogou_Medical = $x("//*[@id=\"mingyi\"]");
    public SelenideElement sogou_Science = $x("//*[@id=\"science\"]");
    public SelenideElement sogou_Hanyu = $x("//*[@id=\"hanyu\"]");
    public SelenideElement sogou_English = $x("//*[@id=\"overseas\"]");
    public SelenideElement sogou_Wenwen = $x("//*[@id=\"index_more_wenwen\"]");
    public SelenideElement sogou_Xueshu = $x("//*[@id=\"scholar\"]");
    public SelenideElement sogou_More = $x("//*[@id=\"more-product\"]");
    public SelenideElement sogou_Weather = $x("//*[@id=\"cur-weather\"]");
    public SelenideElement sogou_ViewCard = $x("//*[@id=\"show-card\"]");
    public SelenideElement sogou_SignIn = $x("//*[@id=\"loginBtn\"]");

    public SelenideElement sogou_Search_SeanrchButton = $x("//*[@id=\"stb\"]");

}
