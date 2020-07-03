package com.coaching.customerapi.controller;

import com.coaching.customerapi.model.Customer;
import com.coaching.customerapi.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/api/customers")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping("/api/customer")
    public Customer getCustomer(@RequestParam Long id){
        return customerService.getCustomer(id);
    }

    @GetMapping("/api/customers")
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }
}
