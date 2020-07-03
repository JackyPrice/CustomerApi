package com.coaching.customerapi.service;

import com.coaching.customerapi.entity.CustomerEntity;
import com.coaching.customerapi.model.Customer;
import com.coaching.customerapi.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        CustomerEntity entity = convertCustomerToCustomerEntity(customer);
        CustomerEntity savedCustomer = customerRepository.save(entity);
        return convertCustomerEntityToCustomer(savedCustomer);
    }

    private CustomerEntity convertCustomerToCustomerEntity(Customer customer) {
        return new CustomerEntity(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getAge());
    }

    private Customer convertCustomerEntityToCustomer(CustomerEntity customerEntity) {
        return new Customer(customerEntity.getId(), customerEntity.getFirstName(), customerEntity.getLastName(), customerEntity.getEmail(), customerEntity.getAge());
    }
}
