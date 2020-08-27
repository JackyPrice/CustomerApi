package com.coaching.customerapi.service;

import com.coaching.customerapi.entity.CustomerEntity;
import com.coaching.customerapi.model.Customer;
import com.coaching.customerapi.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        if (customer.getId() == "" || customer.getId() == null){
            customer.setId(createCustomerUUID());
        }
        CustomerEntity entity = convertCustomerToCustomerEntity(customer);
        return convertCustomerEntityToCustomer(customerRepository.save(entity));
    }

    public Customer getCustomer(String id){
        return convertCustomerEntityToCustomer(customerRepository.findById(id).get());
    }

    public List<Customer> getCustomers() {
        List<CustomerEntity> savedCustomerEntities = customerRepository.findAll();
        List<Customer> customers = new ArrayList<>();
        savedCustomerEntities.forEach(customerEntity -> customers.add(convertCustomerEntityToCustomer(customerEntity)));
        return customers;
    }

    public Customer updateCustomer(Customer customer) {
        CustomerEntity updateCustomer = convertCustomerToCustomerEntity(customer);
        return convertCustomerEntityToCustomer(customerRepository.save(updateCustomer));
    }

    public Customer patchCustomer(Customer customer) {
        CustomerEntity savedCustomerEntity = customerRepository.findById(customer.getId()).get();
        CustomerEntity patchedCustomer = updateFields(customer, savedCustomerEntity);
        return convertCustomerEntityToCustomer(customerRepository.save(patchedCustomer));
    }

    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }


    private CustomerEntity updateFields(Customer customerInput, CustomerEntity savedCustomerEntity) {
        if(customerInput.getFirstName() != null){
            savedCustomerEntity.setFirstName(customerInput.getFirstName());
        }
        if(customerInput.getLastName() != null){
            savedCustomerEntity.setLastName(customerInput.getLastName());
        }
        if(customerInput.getEmail() != null){
            savedCustomerEntity.setEmail(customerInput.getEmail());
        }
        if(customerInput.getAge() != 0){
            savedCustomerEntity.setAge(customerInput.getAge());
        }
        return savedCustomerEntity;
    }

    private String createCustomerUUID(){
        return UUID.randomUUID().toString();
    }
    
    private Customer convertCustomerEntityToCustomer(CustomerEntity customerEntity){
        return Customer.builder()
                .id(customerEntity.getId().toString())
                .firstName(customerEntity.getFirstName())
                .lastName(customerEntity.getLastName())
                .email(customerEntity.getEmail())
                .age(customerEntity.getAge())
                .build();
    }

    private CustomerEntity convertCustomerToCustomerEntity(Customer customer){
        return CustomerEntity.builder()
                // .id(UUID.fromString(customer.getId()))
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .age(customer.getAge())
                .build();
    }
}
