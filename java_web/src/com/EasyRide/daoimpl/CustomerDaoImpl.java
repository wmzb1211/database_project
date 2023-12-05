package com.EasyRide.daoimpl;

import com.EasyRide.dao.CustomerDao;
import com.EasyRide.entity.Customer;
import com.EasyRide.util.DBConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CustomerDaoImpl implements CustomerDao {

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnectionPool.getConnection();
            String sql = "SELECT * FROM customer";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                String name = resultSet.getString("name");
                String account = resultSet.getString("account");
                String password = resultSet.getString("password");
                String contactInfo = resultSet.getString("contact_info");
                String licenseNumber = resultSet.getString("license_number");
                String address = resultSet.getString("address");
                Customer customer = new Customer(customerId, name, account, password, contactInfo, licenseNumber, address);
                customers.add(customer);
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
        return customers;
    }

    @Override
    public Customer getCustomerByLicenseNumber(String licenseNumber) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Customer customer = null;
        try{
            connection = DBConnectionPool.getConnection();
            String sql = "SELECT * FROM customer WHERE license_number = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, licenseNumber);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                String name = resultSet.getString("name");
                String account = resultSet.getString("account");
                String password = resultSet.getString("password");
                String contactInfo = resultSet.getString("contact_info");
                String address = resultSet.getString("address");
                customer = new Customer(customerId, name, account, password, contactInfo, licenseNumber, address);
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
        return customer;
    }

    /**
     * 通过账号获取用户，检查用户是否存在，如果存在则返回用户对象，否则返回null
     */
    @Override
    public Customer getCustomerByAccount(String account){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Customer customer = null;
        try{
            connection = DBConnectionPool.getConnection();
            String sql = "SELECT * FROM customer WHERE account = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                String name = resultSet.getString("name");
                String licenseNumber = resultSet.getString("license_number");
                String password = resultSet.getString("password");
                String contactInfo = resultSet.getString("contact_info");
                String address = resultSet.getString("address");
                customer = new Customer(customerId, name, account, password, contactInfo, licenseNumber, address);
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
        return customer;
    }

    @Override
    public Customer getCustomer(String account, String password) {
        Customer customer = this.getCustomerByAccount(account);
        if (customer != null && customer.getPassword().equals(password)) {
            return customer;
        }
        return null;
    }

    /**
     * 添加用户，如果用户已存在则返回null，否则返回用户对象
     */
    @Override
    public Customer addCustomer(String name, String account, String password, String contactInfo, String licenseNumber, String address){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Customer customer = null;
        try {
            connection = DBConnectionPool.getConnection();
            String sql = "INSERT INTO customer (name, account, password, contact_info, license_number, address) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, account);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, contactInfo);
            preparedStatement.setString(5, licenseNumber);
            preparedStatement.setString(6, address);

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int customerId = resultSet.getInt(1);
                customer = new Customer(customerId, name, account, password, contactInfo, licenseNumber, address);
            }
        } catch (Exception e) {
            // SQLIntegrityConstraintViolationException is thrown when the account already exists
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
        return customer;
    }

    /**
     * 更新用户信息，如果用户不存在或者更改后的账号已存在则返回null，否则返回用户对象
     */
    @Override
    public Customer updateCustomer(Customer customer) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Customer updateCustomer = null;

        try{
            connection = DBConnectionPool.getConnection();
            String sql = "UPDATE customer SET name = ?, account = ?, password = ?, contact_info = ?, license_number = ?, address = ? WHERE customer_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getAccount());
            preparedStatement.setString(3, customer.getPassword());
            preparedStatement.setString(4, customer.getContactInfo());
            preparedStatement.setString(5, customer.getLicenseNumber());
            preparedStatement.setString(6, customer.getAddress());
            preparedStatement.setInt(7, customer.getCustomerId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                updateCustomer = customer;
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

        return updateCustomer;

    }

    @Override
    public boolean deleteCustomer(int customerId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBConnectionPool.getConnection();
            String sql = "DELETE FROM customer WHERE customer_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
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
        CustomerDaoImpl customerDao = new CustomerDaoImpl();


        System.out.println("=== Test getAllCustomers() ===");
        List<Customer> customers = customerDao.getAllCustomers();
        for (Customer customer : customers) {
            System.out.println(customer);
        }

        System.out.println("=== Test getCustomerByLicenseNumber() ===");
        Customer customer = customerDao.getCustomerByLicenseNumber("lic1");
        System.out.println(customer);

        System.out.println("=== Test getCustomerByAccount() ===");
        Customer customer1 = customerDao.getCustomerByAccount("c1");
        System.out.println(customer1);

        System.out.println("=== Test addCustomer() ===");
        Customer customer2 = customerDao.addCustomer("test", "test", "test", "test", "test", "test");
        System.out.println(customer2);

        System.out.println("=== Test updateCustomer() ===");
        customer2.setName("test2");
        customer2.setAccount("test2");
        customer2 = customerDao.updateCustomer(customer2);
        System.out.println(customer2);

        System.out.println("=== Test deleteCustomer() ===");
        boolean result = customerDao.deleteCustomer(customer2.getCustomerId());
        System.out.println(result);
    }
}
