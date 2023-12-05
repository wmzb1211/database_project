package com.EasyRide.dao;

import com.EasyRide.entity.Customer;
import com.EasyRide.util.DBConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CustomerDao {

    /**
     * 获取所有用户
     * @return 用户列表
     */
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

    /**
     * 通过账号和密码获取用户，如果用户存在且密码正确则返回用户对象，否则返回null
     */
    public Customer getCustomer(String account, String password) {
        Customer customer = this.getCustomerByAccount(account);
        if (customer != null && customer.getPassword().equals(password)) {
            return customer;
        }
        return null;
    }

    /**
     * 添加用户，如果用户已存在则返回null，否则返回用户对象
     * @param customer 用户对象, 注意customerId不需要设置或者设置为0
     * @return 添加成功返回Customer对象，其中包含数据库自动生成的customerId，添加失败返回null
     */
    public Customer addCustomer(Customer customer) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Customer newCustomer = null;
        try {
            connection = DBConnectionPool.getConnection();
            String sql = "INSERT INTO customer (name, account, password, contact_info, license_number, address) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getAccount());
            preparedStatement.setString(3, customer.getPassword());
            preparedStatement.setString(4, customer.getContactInfo());
            preparedStatement.setString(5, customer.getLicenseNumber());
            preparedStatement.setString(6, customer.getAddress());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int customerId = resultSet.getInt(1);
                newCustomer = new Customer(customerId, customer.getName(), customer.getAccount(), customer.getPassword(),
                        customer.getContactInfo(), customer.getLicenseNumber(), customer.getAddress());
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
        return newCustomer;
    }

    /**
     * 更新用户信息，如果用户不存在, 或者更改后的账号(account)已存在则返回null，否则返回用户对象
     * 注意：如果用户不存在，或者更改后的账号(account)已存在，则不会更新数据库
     */
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


    /**
     * 删除用户，如果用户不存在则返回false，否则返回true
     */
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
        CustomerDao customerDao = new CustomerDao();


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
        Customer customer2 = customerDao.addCustomer(new Customer(0, "test", "test", "test", "test", "test", "test"));
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
