package com.EasyRide.dao;

import com.EasyRide.entity.Customer;

import java.util.List;

public interface CustomerDao {
    List<Customer> getAllCustomers();
    Customer getCustomerByLicenseNumber(String licenseNumber);
    Customer getCustomerByAccount(String account);
    Customer getCustomer(String account, String password);
    Customer addCustomer(String name, String account, String password, String contactInfo, String licenseNumber, String address);
    Customer updateCustomer(Customer customer);
    boolean deleteCustomer(int customerId);


}
