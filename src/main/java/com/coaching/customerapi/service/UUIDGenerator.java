package com.coaching.customerapi.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDGenerator {
    public String createUUID(){
        return UUID.randomUUID().toString();
    }
}
