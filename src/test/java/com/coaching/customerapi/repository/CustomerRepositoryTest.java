package com.coaching.customerapi.repository;

import com.coaching.customerapi.entity.CustomerEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=validate")
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private static final String TEST_UUID_ID_1 = "123e4567-e89b-12d3-a456-426614174000";

    @Test
    void truetest() {
        assertTrue(true);
    }

    @Test
    @DisplayName("Injected components are not null")
    void checkComponents() {
        assertThat(customerRepository).isNotNull();
    }

    @Test
    @DisplayName("Confirm h2Database is working with default values")
    void checkH2Database() {
        //given
        CustomerEntity expectedCustomer1 = CustomerEntity.builder()
                .id("57ad00c4-5e08-43ca-9630-af962b7580ba")
                .firstName("firstname1")
                .lastName("lastname1")
                .email("email1@address.com")
                .age(20)
                .build();

        CustomerEntity expectedCustomer2 = CustomerEntity.builder()
                .id("e579e2d7-695c-43bb-a0b8-bf8bdd8036bd")
                .firstName("firstname2")
                .lastName("lastname2")
                .email("email2@address.com")
                .age(21)
                .build();

        //when
        CustomerEntity actualCustomer1 = customerRepository.findById("57ad00c4-5e08-43ca-9630-af962b7580ba").get();
        CustomerEntity actualCustomer2 = customerRepository.findById("e579e2d7-695c-43bb-a0b8-bf8bdd8036bd").get();

        //then
        assertThat(customerRepository.findAll()).size().isEqualTo(2);
        assertThat(actualCustomer1).isEqualTo(expectedCustomer1);
        assertThat(actualCustomer2).isEqualTo(expectedCustomer2);
    }

    @Test
    @DisplayName("given valid CustomerEntity when save is called then the customer is saved")
    void saveCustomer() {
        //given
        CustomerEntity testCustomer = CustomerEntity.builder()
                .id(TEST_UUID_ID_1)
                .firstName("TestFirstName")
                .firstName("TestLastName")
                .email("test@test.com")
                .age(25)
                .build();

        //when
        customerRepository.save(testCustomer);

        //then
        assertThat(customerRepository.findById(TEST_UUID_ID_1).get().getId()).isEqualTo(TEST_UUID_ID_1);
    }
}