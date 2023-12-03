package com.EasyRide.dao;

import com.EasyRide.entity.Customer;

import java.util.List;

public interface CustomerDao {
    Customer getCustomerById(int customerId);
    Customer addCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    boolean deleteCustomer(int customerId);
}
