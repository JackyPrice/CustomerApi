package com.coaching.customerapi.service;

import com.coaching.customerapi.model.Customer;

public class CustomerService {


    public Customer createCustomer(Customer customer) {
        return Customer.builder()
                .id(1L)
                .firstName("Test")
                .lastName("Customer")
                .email("test@test.com")
                .age(1)
                .build();
    }
}
