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
    void findAllMethodReturnsExpectedCustomers(){
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

    @Test
    @DisplayName("given a valid id, when deleteById is called, then the entry is deleted")
    void testDeleteByIdDeletesCorrectEntry(){

//        given
        List<CustomerEntity> initialCustomerEntityList = List.of(
                new CustomerEntity(100L, "firstname1", "lastname1", "email1@address.com", 20),
                new CustomerEntity(101L, "firstname2", "lastname2", "email2@address.com", 21));
        List<CustomerEntity> expectedCustomerEntityList = List.of(
                new CustomerEntity(100L, "firstname1", "lastname1", "email1@address.com", 20));
//        when
        List<CustomerEntity> initialDatabaseCustomerEntityList = customerRepository.findAll();
//        then
        assertThat(initialDatabaseCustomerEntityList.size()).isEqualTo(2);
        assertThat(initialDatabaseCustomerEntityList).containsExactlyInAnyOrder(initialCustomerEntityList.get(0), initialCustomerEntityList.get(1));

//        when
        customerRepository.deleteById(101L);
        List<CustomerEntity> actualDatabaseCustomerEntityList = customerRepository.findAll();
        assertThat(actualDatabaseCustomerEntityList.size()).isEqualTo(1);
        assertThat(actualDatabaseCustomerEntityList).containsExactlyInAnyOrder(actualDatabaseCustomerEntityList.get(0));
    }
}