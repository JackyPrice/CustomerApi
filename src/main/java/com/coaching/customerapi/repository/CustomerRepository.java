package com.coaching.customerapi.repository;

import com.coaching.customerapi.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {

}
