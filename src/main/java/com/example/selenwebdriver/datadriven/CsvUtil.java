package com.example.selenwebdriver.datadriven;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {

    public static Object[][] getData(String csvFilePath) throws IOException {
        // 知道了 csvFile 文件路径，读取它之前，要先定义一个 FileInputStream 文件输入流对象，层级：低级，字节流
        FileInputStream csvFileInputStream = new FileInputStream(csvFilePath);
        // 有了 FileInputStream 之后，要定义一个 Reader 输入流读取器对象，从文件输入流中读取数据，层级：中级，字符流
        //  设定 UTF_8 字符集
        Reader reader = new InputStreamReader(csvFileInputStream, StandardCharsets.UTF_8);
        // 有了 Reader 输入流读取器对象之后，定义一个 文本行 缓冲读取器对象，调用 readLine 按行读取，层级：高级，文本行 (即文本中一行字符串)
        //  用 readLine 按行读取到的 文本行数据，存放在 内存上，需要定义一个 List 容器对象 来进行存储
        BufferedReader bufferedReader = new BufferedReader(reader);
        // 这一行在这里的作用是，忽略读取 第一行（即 标题行）
        bufferedReader.readLine();
        // 定义一个 List 容器对象来存储 文本行内容 (即文本中每一行字符串)，遍历读取 除标题行之外的 每一行文本内容
        List<Object[]> records = new ArrayList<Object[]>();
        String record;
        while ((record = bufferedReader.readLine()) != null) {
            // 原本是一行字符串，分割成 3 段，需要用到 String[] 字符串一维数组
            //  注意：分割符 要和 CSV 文件中使用的格式（区分为 中文、英文）保持一致，
            //   否则会 提示报错 org.testng.internal.reflect.MethodMatcherException: ... Data provider mismatch
            String[] fields = record.split(",");
            records.add(fields);
        }
        // 关闭 读取器对象，切记 ！！！
        bufferedReader.close();
        reader.close();

        // 定义 函数返回值，即 二维数组 Object[][]
        //  二维数组 等同于 一维数组中的元素 仍然是 一维数组，这样容易理解和运用
        //   将 List 容器对象（容器中元素已经是 一维数组）转换为 二维数组
        Object[][] dataInCsvFile = new Object[records.size()][];
        for (int i = 0; i < records.size(); i++) {
            dataInCsvFile[i] = records.get(i);
        }

        return dataInCsvFile;
    }
}
