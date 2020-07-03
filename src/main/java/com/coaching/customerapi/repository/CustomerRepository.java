package com.coaching.customerapi.repository;

import com.coaching.customerapi.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {
    List<CustomerEntity> findAll();
    void deleteById(Long id);
}
