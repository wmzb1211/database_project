package com.EasyRide.dao;

import com.EasyRide.entity.Administrator;
import com.EasyRide.util.DBConnectionPool;

import java.sql.*;

public class AdministratorDao {

    /**
     * Get administrator by username and password
     * 如果用户名和密码匹配，返回Administrator对象，否则返回null
     * @param username 用户名
     * @param password 密码
     * @return Administrator对象
     */
    public Administrator getAdministrator(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Administrator administrator = null;

        try{
            connection = DBConnectionPool.getConnection();
            String sql = "SELECT * FROM administrator WHERE account = ? AND password = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int adminId = resultSet.getInt("admin_id");
                String name = resultSet.getString("name");
                String account = resultSet.getString("account");
                String contactInfo = resultSet.getString("contact_info");
                String role = resultSet.getString("role");
                Date creationDate = resultSet.getDate("creation_date");
                administrator = new Administrator(adminId, name, account, password, contactInfo, role, creationDate);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnectionPool.releaseConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return administrator;
    }

    /**
     * 添加管理员
     * @param administrator Administrator对象，注意adminId不需要设置或者设置为0
     * @return 添加成功返回Administrator对象，其中包含数据库自动生成的adminId，添加失败返回null
     */
    public Administrator addAdministrator(Administrator administrator) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Administrator addAdministrator = null;

        try{
            connection = DBConnectionPool.getConnection();
            Date creationDate = new Date(System.currentTimeMillis());
            String sql = "INSERT INTO administrator(name, account, password, contact_info, role, creation_date) VALUES(?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, administrator.getName());
            preparedStatement.setString(2, administrator.getAccount());
            preparedStatement.setString(3, administrator.getPassword());
            preparedStatement.setString(4, administrator.getContactInfo());
            preparedStatement.setString(5, administrator.getRole());
            preparedStatement.setDate(6, creationDate);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                int adminId = resultSet.getInt(1);
                addAdministrator = new Administrator(adminId, administrator.getName(), administrator.getAccount(),
                        administrator.getPassword(), administrator.getContactInfo(), administrator.getRole(), creationDate);
            }

        }catch (SQLException e){
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Account already exists");
            } else {
                e.printStackTrace();
            }
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnectionPool.releaseConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return addAdministrator;
    }


    /**
     * 更新管理员信息
     * @param administrator Administrator对象，其中adminId必须正确，不能修改
     * @return 更新成功返回Administrator对象，否则返回null
     */
    public Administrator updateAdministrator(Administrator administrator) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Administrator updateAdministrator = null;

        try{
            connection = DBConnectionPool.getConnection();
            String sql = "UPDATE administrator SET name = ?, account = ?, password = ?, contact_info = ?, role = ? WHERE admin_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, administrator.getName());
            preparedStatement.setString(2, administrator.getAccount());
            preparedStatement.setString(3, administrator.getPassword());
            preparedStatement.setString(4, administrator.getContactInfo());
            preparedStatement.setString(5, administrator.getRole());
            preparedStatement.setInt(6, administrator.getAdminId());
            preparedStatement.executeUpdate();
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                updateAdministrator = administrator;
            }
        } catch (Exception e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Account already exists");
            } else {
                e.printStackTrace();
            }
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnectionPool.releaseConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return updateAdministrator;
    }


    /**
     * 删除管理员
     * @param adminId 管理员ID
     * @return 删除成功返回true，否则返回false
     */
    public boolean deleteAdministrator(int adminId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBConnectionPool.getConnection();
            String sql = "DELETE FROM administrator WHERE admin_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, adminId);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnectionPool.releaseConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // 测试代码
    public static void main(String[] args) {
        AdministratorDao administratorDao = new AdministratorDao();

        System.out.println("=== Test getAdministrator() ===");
        Administrator administrator = administratorDao.getAdministrator("admin", "passwd");
        System.out.println(administrator);

        System.out.println("=== Test addAdministrator() ===");
        administrator = new Administrator(0, "test", "test", "test", "test", "test", null);
        administrator = administratorDao.addAdministrator(administrator);
        System.out.println(administrator);

        System.out.println("=== Test updateAdministrator() ===");
        administrator = administratorDao.updateAdministrator(administrator);
        System.out.println(administrator);

        System.out.println("=== Test deleteAdministrator() ===");
        boolean result = administratorDao.deleteAdministrator(administrator.getAdminId());
        System.out.println(result);
    }
}
