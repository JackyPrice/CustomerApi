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
    void findAllMethodReturnsExpectedCustomers() {
        // given
        List<CustomerEntity> expectedCustomerEntityList = List.of(
                CustomerEntity.builder().id(100L).firstName("firstname1").lastName("lastname1").email("email1@address.com").age(20).build(),
                CustomerEntity.builder().id(101L).firstName("firstname2").lastName("lastname2").email("email2@address.com").age(21).build());

        // when
        List<CustomerEntity> databaseCustomerEntityList = customerRepository.findAll();

        // then
        assertThat(databaseCustomerEntityList.size()).isEqualTo(2);
        assertThat(databaseCustomerEntityList).containsExactlyInAnyOrder(expectedCustomerEntityList.get(0), expectedCustomerEntityList.get(1));
    }

    @Test
    @DisplayName("given a valid id, when deleteById is called, then the entry is deleted")
    void testDeleteByIdDeletesCorrectEntryWithDefaultData() {

        // given
        List<CustomerEntity> expectedCustomerEntityList = List.of(
                CustomerEntity.builder().id(100L).firstName("firstname1").lastName("lastname1").email("email1@address.com").age(20).build());

        // when
        customerRepository.deleteById(101L);
        List<CustomerEntity> actualDatabaseCustomerEntityList = customerRepository.findAll();

        // then
        assertThat(actualDatabaseCustomerEntityList.size()).isEqualTo(1);
        assertThat(actualDatabaseCustomerEntityList).containsExactlyInAnyOrder(expectedCustomerEntityList.get(0));
    }
}