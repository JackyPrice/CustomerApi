package com.coaching.customerapi.controller;

import com.coaching.customerapi.model.Customer;
import com.coaching.customerapi.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping("/api/customers")
    public Customer createCustomer(@RequestBody Customer customerToSave){
        return customerService.createCustomer(customerToSave);
    }

    @GetMapping("/api/customer/{id}")
    public Customer getCustomer(@PathVariable("id") Long id){
        return customerService.getCustomer(id);
    }

    @GetMapping("/api/customers")
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    @PutMapping("/api/customers")
    public Customer updateCustomer(@RequestBody Customer customer){
        return customerService.updateCustomer(customer);
    }

    @PatchMapping("/api/customers")
    public Customer patchCustomer(@RequestBody Customer customer){
        return customerService.patchCustomer(customer);
    }

    @DeleteMapping("/api/customers/{id}")
    public void deleteCustomer(@PathVariable("id") Long id){
        customerService.deleteCustomer(id);
    }
}
