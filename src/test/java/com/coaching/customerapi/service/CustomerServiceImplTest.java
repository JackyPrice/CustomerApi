package com.coaching.customerapi.service;

import com.coaching.customerapi.entity.CustomerEntity;
import com.coaching.customerapi.repository.CustomerRepository;
import com.coaching.customerapi.service.CustomerServiceImpl;
import com.coaching.customerapi.model.Customer;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@Data
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private UUIDGenerator uuidGenerator;

    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setup() {
        this.customerService = new CustomerServiceImpl(customerRepository, uuidGenerator);
    }

    private static final String TEST_UUID_ID_1 = "123e4567-e89b-12d3-a456-426614174000";
    private static final String TEST_UUID_ID_2 = "123e4567-e89b-12d3-a456-426614174001";
    private static final String TEST_UUID_ID_3 = "123e4567-e89b-12d3-a456-426614174001";


    @Test
    @DisplayName("given a valid Customer when the create customer method is called then it saves this customer in the database and returns a valid customer")
    public void createCustomer() {
        // given

        Customer customerToBeSaved = Customer.builder().firstName("TestFirstName").lastName("TestSecondName").email("test@test.com").age(30).build();
        CustomerEntity customerEntityToBeSaved = CustomerEntity.builder().id(TEST_UUID_ID_1).firstName("TestFirstName").lastName("TestSecondName").email("test@test.com").age(30).build();

        CustomerEntity savedCustomerEntity = CustomerEntity.builder().id(TEST_UUID_ID_1).firstName("TestFirstName").lastName("TestSecondName").email("test@test.com").age(30).build();
        Customer expectedSavedCustomer = Customer.builder().id(TEST_UUID_ID_1).firstName("TestFirstName").lastName("TestSecondName").email("test@test.com").age(30).build();

        when(uuidGenerator.createUUID()).thenReturn(TEST_UUID_ID_1);
        when(customerRepository.save(customerEntityToBeSaved)).thenReturn(savedCustomerEntity);

        // when
        Customer actualSavedCustomer = customerService.createCustomer(customerToBeSaved);

        // then
        verify(customerRepository).save(customerEntityToBeSaved); //  did the customer repository save method get called with this entity
        assertEquals(expectedSavedCustomer, actualSavedCustomer);
    }

    @Test
    @DisplayName("give valid id, when the getCustomer method is called then it returns the customer with the same id")
    public void getCustomer() {
        // given
        CustomerEntity savedCustomerEntity = CustomerEntity.builder().id(TEST_UUID_ID_2).firstName("Firstname").lastName("SecondName").email("email@email.com").age(20).build();
        Customer expectedCustomer = Customer.builder().id(TEST_UUID_ID_2).firstName("Firstname").lastName("SecondName").email("email@email.com").age(20).build();

        when(customerRepository.findById(TEST_UUID_ID_2)).thenReturn(Optional.of(savedCustomerEntity));

        // when
        Customer actualCustomer = customerService.getCustomer(TEST_UUID_ID_2);

        // then
        assertEquals(expectedCustomer, actualCustomer);
    }

    @Test
    @DisplayName("given request for all customers, when get customers method is called, a full list of customers is returned")
    public void getCustomers() {
        // given
        List<Customer> expectedCustomerList = List.of(
                Customer.builder().id(TEST_UUID_ID_1).firstName("Squall").lastName("Leonhart").email("test@test.com").age(17).build(),
                Customer.builder().id(TEST_UUID_ID_2).firstName("Cloud").lastName("Strife").email("test@test.com").age(24).build(),
                Customer.builder().id(TEST_UUID_ID_3).firstName("Noctis").lastName("Caelum").email("test@test.com").age(20).build()
        );

        when(customerRepository.findAll()).thenReturn(List.of(
                CustomerEntity.builder().id(TEST_UUID_ID_1).firstName("Squall").lastName("Leonhart").email("test@test.com").age(17).build(),
                CustomerEntity.builder().id(TEST_UUID_ID_2).firstName("Cloud").lastName("Strife").email("test@test.com").age(24).build(),
                CustomerEntity.builder().id(TEST_UUID_ID_3).firstName("Noctis").lastName("Caelum").email("test@test.com").age(20).build()
        ));

        // when
        List<Customer> actualCustomerList = customerService.getCustomers();

        // then
        assertEquals(expectedCustomerList, actualCustomerList);
    }

    @Test
    @DisplayName("given valid customer when updated then customer in returned is the updated customer")
    public void updateCustomer() {
        // given
        Customer updateCustomerInput = Customer.builder().firstName("updated").lastName("customer").email("updated@test.com").age(20).build();
        CustomerEntity updateCustomerEntity = CustomerEntity.builder().firstName("updated").lastName("customer").email("updated@test.com").age(20).build();
        CustomerEntity updatedCustomer = CustomerEntity.builder().id(TEST_UUID_ID_1).firstName("updated").lastName("customer").email("updated@test.com").age(20).build();
        Customer expectedCustomer = Customer.builder().id(TEST_UUID_ID_1).firstName("updated").lastName("customer").email("updated@test.com").age(20).build();

        when(customerRepository.save(updateCustomerEntity)).thenReturn(updatedCustomer);

        // when
        Customer actualCustomer = customerService.updateCustomer(updateCustomerInput);

        // then
        assertEquals(expectedCustomer, actualCustomer);
    }

    @Test
    @DisplayName("given valid customer fields when patchCustomer is called then the correct changes are made to the Customer")
    public void patchCustomer() {
        // given
        Customer patchCustomerInput = Customer.builder().id(TEST_UUID_ID_1).firstName("patchedname").build();
        CustomerEntity savedCustomer = CustomerEntity.builder().id(TEST_UUID_ID_1).firstName("fistname").lastName("lastname").email("email@email.com").age(1).build();
        CustomerEntity patchedCustomer = CustomerEntity.builder().id(TEST_UUID_ID_1).firstName("patchedname").lastName("lastname").email("email@email.com").age(1).build();
        Customer expectedCustomer = Customer.builder().id(TEST_UUID_ID_1).firstName("patchedname").lastName("lastname").email("email@email.com").age(1).build();

        when(customerRepository.findById(TEST_UUID_ID_1)).thenReturn(Optional.of(savedCustomer));
        when(customerRepository.save(patchedCustomer)).thenReturn(patchedCustomer);

        // when
        Customer actualCustomer = customerService.patchCustomer(patchCustomerInput);

        // then
        assertEquals(expectedCustomer, actualCustomer);
    }


    @ParameterizedTest
    @ValueSource(strings = {TEST_UUID_ID_1, TEST_UUID_ID_2, TEST_UUID_ID_3})
    @DisplayName("given valid id, when deleted then doesn't return a value")
    public void deleteCustomer(String input) {
        // given

        // when
        customerService.deleteCustomer(input);
        // then
        verify(customerRepository).deleteById(input);
    }
}