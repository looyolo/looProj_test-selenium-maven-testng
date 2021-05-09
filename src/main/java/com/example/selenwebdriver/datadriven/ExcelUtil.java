package com.example.selenwebdriver.datadriven;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {
    public static Object[][] getData(String excelFilePath, String sheetNameInWorkbook) throws IOException {
        // 读取 Excel 文件的内容
        //  定义 FileInputStream 对象，用于读取 Excel 文件
        InputStream fileInputStream = new FileInputStream(excelFilePath);

        // 定义工作簿
        //  已知的 Excel 文件扩展名 有 2 种，分别是 .xls 和 .xlsx ，需要区分处理，所以先要 获取 扩展名，方便下一步判断处理
        //   调用 <String对象>.substring 截取 Excel 文件扩展名，索引的 "." 号必须得是最后一个，不排除文件路径中目录名称也包含有 "." 号的可能
        String excelFileExtensionName = excelFilePath.substring(excelFilePath.lastIndexOf("."));
        //    判断 Excel 文件扩展名，生成相应的 WorkBook 工作簿对象
        Workbook workbook = null;
        if (excelFileExtensionName.equals(".xls")) {
            workbook = new HSSFWorkbook(fileInputStream);
        } else if (excelFileExtensionName.equals(".xlsx")) {
            workbook = new XSSFWorkbook(fileInputStream);
        }

        // 定义工作表
        //  判断 工作表名 是否符合
        Sheet sheet;
        if (sheetNameInWorkbook.equals("")) {
            // 默认取第一个子表
            sheet = workbook.getSheetAt(0);
        } else {
            sheet = workbook.getSheet(sheetNameInWorkbook);
        }

        // 定义一个 List 容器对象，用来存放 每行每个单元格 的内容
        List<Object[]> records = new ArrayList<Object[]>();
        //  获取 总行数，再进一步 循环获取 每行每个单元格 的内容
        //   注意： Excel 文件的 行号、列号 都是从 0 开始的。默认第一行为标题行，index = 0 , 所以，读取 行数据是从第 index = 1 开始
        //         在没有合并单元格的情形下，通过定义 "int rowCount = getPhysicalNumberOfRows()" 直接得出 （物理）行总数 即可
        //         在出现合并单元格时，通过定义 "int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum() + 1" 计算得出 （逻辑）行总数
        //         对于 列总数（即 单元格总数），处理逻辑也一样。
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowIndex = 1; rowIndex < rowCount; rowIndex++) {
            // 定义 当前行
            Row currentRow = sheet.getRow(rowIndex);
            //  判断 当前行 是否为 空白行，直接跳出当前循环，进入下一个循环
            if (currentRow == null) {
                continue;
            }

            // 获取 当前行 里面的 单元格总数
            int cellCountInCurrentRow = currentRow.getPhysicalNumberOfCells();
            //  定义一个 String[] 一维数组，数组长度 等于 当前行 里面的 单元格总数，用来存放 单元格的内容。
            //   注意：每迭代一次 当前行 ，就生成一个 String[] 数组对象。
            String[] fields = new String[cellCountInCurrentRow];
            //    循环获取 当前行 每个单元格 的内容
            for (int cellIndex = 0; cellIndex < cellCountInCurrentRow; cellIndex++) {
                Cell cell = currentRow.getCell(cellIndex);
                // 判断 单元格 的内容 是否为 字符串类型
                if (cell.getCellType() == CellType.STRING) {
                    fields[cellIndex] = cell.getStringCellValue();
                }
                // 判断 单元格 的内容 是否为 日期类型，是的话，简单日期格式化后，转为字符串
                if (cell.getCellType() == CellType.NUMERIC) {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        fields[cellIndex] = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(cell.getDateCellValue());
                    }
                }
            }
            records.add(fields);
        }

        // 关闭 流对象，切记 ！！！
        fileInputStream.close();

        // 定义 函数返回值，即 二维数组 Object[][]
        //  二维数组 等同于 一维数组中的元素 仍然是 一维数组，这样容易理解和运用
        //   将 List 容器对象（容器中元素已经是 一维数组）转换为 二维数组
        Object[][] dataInExcelFile = new Object[records.size()][];
        for (int i = 0; i < records.size(); i++) {
            dataInExcelFile[i] = records.get(i);
        }

        return dataInExcelFile;
    }
}
