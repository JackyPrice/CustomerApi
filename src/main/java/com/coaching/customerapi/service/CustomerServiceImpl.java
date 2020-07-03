package com.coaching.customerapi.service;

import com.coaching.customerapi.entity.CustomerEntity;
import com.coaching.customerapi.model.Customer;
import com.coaching.customerapi.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private CustomerEntity savedCustomer;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        CustomerEntity entity = convertCustomerToCustomerEntity(customer);
        savedCustomer = customerRepository.save(entity);
        return convertCustomerEntityToCustomer(savedCustomer);
    }

    @Override
    public Customer getCustomer(Long id) {
        savedCustomer = customerRepository.findById(id).get();
        return convertCustomerEntityToCustomer(savedCustomer);
    }

    @Override
    public List<Customer> getCustomers() {
        List<CustomerEntity> savedCustomerList = customerRepository.findAll();
        List<Customer> customerList = new ArrayList<>();
        savedCustomerList.forEach(customerEntity -> customerList.add(convertCustomerEntityToCustomer(customerEntity)));
        return customerList;
    }

    private CustomerEntity convertCustomerToCustomerEntity(Customer customer) {
        return new CustomerEntity(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getAge());
    }

    private Customer convertCustomerEntityToCustomer(CustomerEntity customerEntity) {
        return new Customer(customerEntity.getId(), customerEntity.getFirstName(), customerEntity.getLastName(), customerEntity.getEmail(), customerEntity.getAge());
    }
}
