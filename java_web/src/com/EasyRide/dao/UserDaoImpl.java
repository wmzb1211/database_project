package com.EasyRide.dao;


import com.EasyRide.entity.User;
import com.EasyRide.util.DBConnectionPool;

import java.sql.*;

// UserDaoImpl.java
public class UserDaoImpl implements UserDao {
    @Override
    public User getUserByUsername(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = DBConnectionPool.getConnection();
            String sql = "SELECT * FROM user WHERE username = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String passwordHash = resultSet.getString("password_hash");
                String email = resultSet.getString("email");
                Date registrationDate = new Date(new java.util.Date().getTime());
                Date lastLoginDate = new Date(new java.util.Date().getTime());
                String role = resultSet.getString("role");
                user = new User(id, username, passwordHash, email, registrationDate.toString(), lastLoginDate.toString(), role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnectionPool.releaseConnection(connection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public User addUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User newUser = null;

        int id = -1;
        try{
            connection = DBConnectionPool.getConnection();
            String sql = "INSERT INTO user (username, password_hash, email, registration_date, last_login_date, role) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPasswordHash());
            preparedStatement.setString(3, user.getEmail());
            Date registration_date = new Date(new java.util.Date().getTime());
            Date last_login_date = new Date(new java.util.Date().getTime());
            preparedStatement.setDate(4, registration_date);
            preparedStatement.setDate(5, last_login_date);
            preparedStatement.setString(6, user.getRole());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new Exception("Creating user failed, no rows affected.");
            }

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            } else {
                throw new Exception("Creating user failed, no ID obtained.");
            }

            newUser = new User(id, user.getUsername(), user.getPasswordHash(), user.getEmail(), registration_date.toString(), last_login_date.toString(), user.getRole());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnectionPool.releaseConnection(connection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newUser;
    }
    // 实现数据库操作方法
}

