package com.example.selenwebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TableCommonOperations {
    protected WebElement _table;

    public TableCommonOperations(WebElement webTable) {
        _table = webTable;
    }
    // 获取 表头元素
    public List<WebElement> getTableHeadElements() {
        List<WebElement> _tableHeadElements = _table.findElements(By.tagName("th"));
        return _tableHeadElements;
    }
    // 获取 表格行数
    public int getTableRows() {
        List<WebElement> _tableRows = _table.findElements(By.tagName("tr"));
        return _tableRows.size();
    }
    // 获取 表格列数
    public int getTableColumns() {
        List<WebElement> _tableRows = _table.findElements(By.tagName("tr"));
        List<WebElement> _tableColumns = _tableRows.get(0).findElements(By.tagName("td"));
        return _tableColumns.size();
    }
    // 定位到 表格中 某行某列 的 单元格
    public WebElement getTableCellElement(int rowNum, int columnNum) {
        List<WebElement> _tableRows = _table.findElements(By.tagName("tr"));
        List<WebElement> _tableColumns = _tableRows.get(rowNum - 1).findElements(By.tagName("td"));
        return _tableColumns.get(columnNum - 1);
    }
    // 获取 表格中 某行某列 的 单元格中 内容
    public String getTextInTableCell(int rowNum, int columnNum) {
        List<WebElement> _tableRows = _table.findElements(By.tagName("tr"));
        List<WebElement> _tableColumns = _tableRows.get(rowNum - 1).findElements(By.tagName("td"));
        return _tableColumns.get(columnNum - 1).getText();
    }
    // 获取 表格中 某行某列 单元格中 某个页面元素对象，有待后续需要时实现，By 入参为 定位器对象
    //  public String getWebElementInTableCell(int rowNum, int columnNum，By by)
}
