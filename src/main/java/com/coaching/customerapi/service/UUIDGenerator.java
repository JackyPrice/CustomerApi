package com.coaching.customerapi.service;

import com.coaching.customerapi.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDGenerator {
    private final CustomerRepository customerRepository;

    public UUIDGenerator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public String createUUID(){
        String id = UUID.randomUUID().toString();
        return verifyUniqueId(id);
    }

    private String verifyUniqueId(String id) {
        while (customerRepository.existsById(id)) {
            id = UUID.randomUUID().toString();
        }
        return id;
    }
}
