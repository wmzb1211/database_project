package com.EasyRide.daoimpl;

import com.EasyRide.dao.AdministratorDao;
import com.EasyRide.entity.Administrator;
import com.EasyRide.util.DBConnectionPool;

import java.sql.*;

public class AdministratorDaoImpl implements AdministratorDao {

    @Override
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

    @Override
    public Administrator addAdministrator(String name, String account, String password, String contactInfo, String role) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Administrator administrator = null;

        try{
            connection = DBConnectionPool.getConnection();
            Date creationDate = new Date(System.currentTimeMillis());
            String sql = "INSERT INTO administrator(name, account, password, contact_info, role, creation_date) VALUES(?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, account);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, contactInfo);
            preparedStatement.setString(5, role);
            preparedStatement.setDate(6, creationDate);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                int adminId = resultSet.getInt(1);
                administrator = new Administrator(adminId, name, account, password, contactInfo, role, creationDate);
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
        return administrator;
    }

    @Override
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

    @Override
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
        AdministratorDaoImpl administratorDaoImpl = new AdministratorDaoImpl();

        System.out.println("=== Test getAdministrator() ===");
        Administrator administrator = administratorDaoImpl.getAdministrator("admin", "passwd");
        System.out.println(administrator);

        System.out.println("=== Test addAdministrator() ===");
        administrator = administratorDaoImpl.addAdministrator("admin2", "admin2", "admin2passwd", "admin", "admin");
        System.out.println(administrator);

        System.out.println("=== Test updateAdministrator() ===");
        administrator = administratorDaoImpl.updateAdministrator(administrator);
        System.out.println(administrator);

        System.out.println("=== Test deleteAdministrator() ===");
        boolean result = administratorDaoImpl.deleteAdministrator(administrator.getAdminId());
        System.out.println(result);
    }
}
