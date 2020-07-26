package com.coaching.customerapi.repository;

import com.coaching.customerapi.entity.CustomerEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    @DisplayName("given call to findAll method, when database is called then return 2 customers ")
    public void findAllMethodReturnsExpectedCustomers(){
//      given
        List<CustomerEntity> expectedCustomerEntityList = List.of(
                new CustomerEntity(100L, "firstname1", "lastname1", "email1@address.com", 20),
                new CustomerEntity(101L, "firstname2", "lastname2", "email2@address.com", 21));
//      when
        List<CustomerEntity> databaseCustomerEntityList =  customerRepository.findAll();
//      then
        assertThat(databaseCustomerEntityList.size()).isEqualTo(2);
        assertThat(databaseCustomerEntityList).containsExactlyInAnyOrder(expectedCustomerEntityList.get(0), expectedCustomerEntityList.get(1));
    }


}