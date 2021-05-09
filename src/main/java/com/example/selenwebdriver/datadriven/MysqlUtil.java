package com.example.selenwebdriver.datadriven;

import org.junit.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlUtil {
    // 定义 MySQL jdbc connection 对象。注意：生产环境中，建议使用 non-root 账号
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/selenwebdriver_datadriven"
            + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String USER_NAME = "root";
    static final String USER_PASSWORD = "root";

    public static Object[][] getData(String dbTableName) throws ClassNotFoundException, SQLException {
        // 注册（或说 加载） MySQL 驱动程序
        Class.forName(JDBC_DRIVER);
        // 定义 Connection 连接对象
        Connection connection = DriverManager.getConnection(JDBC_URL, USER_NAME, USER_PASSWORD);
        Assert.assertFalse(connection.isClosed());
        // 定义 Statement 语句对象
        Statement statement = connection.createStatement();
        // 定义 sql 字符串
        String sql = "select * from " + dbTableName + ";";
        // 调用 Statement 语句对象的 executeQuery 执行查询 sql 字符串，返回一个 ResultSet 结果集对象
        ResultSet resultSet = statement.executeQuery(sql);

        // 定义一个 List 容器对象，用来存放 结果集对象中的内容
        List<Object[]> records = new ArrayList<Object[]>();
        //  获取 字段总数，并且 每行结果 的 字段总数 一定是相等的，所以只需要获取一次即可，跟 Excel 有合并单元格时，情形不一样。
        //   区别于 读取 Excel 文件时的情形，这里不需要关注 rowCount 总行数，因为 结果集对象 是随着每次查询而 scrollable 的
        int fieldCount = resultSet.getMetaData().getColumnCount();
        //    在不确定 行总数 情形下，循环获取 结果集对象中每一行内容。注意：结果集对象中 索引 是从 1 开始
        while (resultSet.next()) {
            //  定义一个 Object[] 一维数组，数组长度 等于 结果集对象中 字段总数，用来存放 结果集对象中的内容。
            //   注意：每读取一行结果，就生成一个 Object[] 数组对象。另外，这里已经不再仅仅限于 String 类。
            Object[] fields = new Object[fieldCount];
            for (int fieldIndex = 0; fieldIndex < fieldCount; fieldIndex++) {
                // 字段类型 很多，这里示例高频使用的 4 种
                //  判断 字段类型 是否为 字符串类型
                if (resultSet.getMetaData().getColumnType(fieldIndex + 1) == Types.VARCHAR) {
                    fields[fieldIndex] = resultSet.getString(fieldIndex + 1);
                }
                //  判断 字段类型 是否为 实数类型
                if (resultSet.getMetaData().getColumnType(fieldIndex + 1) == Types.DECIMAL) {
                    fields[fieldIndex] = resultSet.getBigDecimal(fieldIndex + 1);
                }
                //  判断 字段类型 是否为 日期类型
                if (resultSet.getMetaData().getColumnType(fieldIndex + 1) == Types.DATE) {
                    fields[fieldIndex] = resultSet.getDate(fieldIndex + 1);
                }
                //  判断 字段类型 是否为 时间戳类型
                if (resultSet.getMetaData().getColumnType(fieldIndex + 1) == Types.TIMESTAMP) {
                    fields[fieldIndex] = resultSet.getTimestamp(fieldIndex + 1);
                }
            }
            records.add(fields);
        }

        // 关闭 连接对象，切记 ！！！
        resultSet.close();
        statement.close();
        connection.close();

        // 定义 函数返回值，即 二维数组 Object[][]
        //  二维数组 等同于 一维数组中的元素 仍然是 一维数组，这样容易理解和运用
        //   将 List 容器对象（容器中元素已经是 一维数组）转换为 二维数组
        Object[][] dataInDB = new Object[records.size()][];
        for (int i = 0; i < records.size(); i++) {
            dataInDB[i] = records.get(i);
        }

        return dataInDB;
    }
}
