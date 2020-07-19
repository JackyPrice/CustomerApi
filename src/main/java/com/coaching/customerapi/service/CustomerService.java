package com.coaching.customerapi.service;

import com.coaching.customerapi.model.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    Customer patchCustomer(Customer customer);
    Customer getCustomer(Long id);
    List<Customer> getCustomers();
    String deleteCustomer(Long id);
}
