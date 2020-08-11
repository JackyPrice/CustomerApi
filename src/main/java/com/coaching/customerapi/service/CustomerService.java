package com.coaching.customerapi.service;

import com.coaching.customerapi.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer getCustomer(Long id);
    List<Customer> getCustomers();
    Customer updateCustomer(Customer customer);
    Customer patchCustomer(Customer customer);
    void deleteCustomer(Long id);
}
