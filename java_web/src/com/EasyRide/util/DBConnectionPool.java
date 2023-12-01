package com.EasyRide.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBConnectionPool {
    private static final String url = "jdbc:mysql://localhost:3306/cardb";
    private static final String username = "carmaster";
    private static final String password = "carmasterpasswd";
    private static final int initialPoolSize = 5; // 初始连接池大小
    private static final int maxPoolSize = 10;    // 最大连接池大小

    private static List<Connection> connectionPool = new ArrayList<>();
    private static List<Connection> usedConnections = new ArrayList<>();

    static {
        try {
            for (int i = 0; i < initialPoolSize; i++) {
                Connection connection = DriverManager.getConnection(url, username, password);
                connectionPool.add(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < maxPoolSize) {
                Connection connection = DriverManager.getConnection(url, username, password);
                usedConnections.add(connection);
                return connection;
            } else {
                throw new SQLException("Connection pool exhausted.");
            }
        }

        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public static synchronized void releaseConnection(Connection connection) {
        usedConnections.remove(connection);
        connectionPool.add(connection);
    }

    public static void main(String[] args) {
        // 创建数据库连接池
        DBConnectionPool connectionPool = new DBConnectionPool();

        try {
            // 从连接池获取数据库连接
            Connection connection = connectionPool.getConnection();

            // 向 Customer 表插入一条记录
//            CREATE TABLE Customer (
//                    customer_id INT PRIMARY KEY,
//                    name VARCHAR(255),
//                    contact_info VARCHAR(255),
//                    license_number VARCHAR(20),
//                    address VARCHAR(255)
//            );
            String sql = "INSERT INTO Customer VALUES (1, '张三', '13812345678', '京A12345', '北京市海淀区')";
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

            // 查询 Customer 表的记录
            sql = "SELECT * FROM Customer";
            preparedStatement = connection.prepareStatement(sql);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }


            // 释放数据库连接
            preparedStatement.close();
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


