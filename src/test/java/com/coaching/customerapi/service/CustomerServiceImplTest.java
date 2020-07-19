package com.coaching.customerapi.service;

import com.coaching.customerapi.entity.CustomerEntity;
import com.coaching.customerapi.model.Customer;
import com.coaching.customerapi.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("given a valid Customer when the create customer method is called then it saves this customer in the database and returns a customer with a valid ID")
    public void createCustomer() {
        //given
        Customer expectedSavedCustomer = new Customer(1L, "TestFirstName", "TestSecondName", "test@test.com", 30);
        CustomerEntity customerEntityToBeSaved = new CustomerEntity(null, "TestFirstName", "TestSecondName", "test@test.com", 30);
        CustomerEntity savedCustomerEntity = new CustomerEntity(1L, "TestFirstName", "TestSecondName", "test@test.com", 30);
        Customer customerToBeSaved = new Customer(null, "TestFirstName", "TestSecondName", "test@test.com", 30);

        CustomerServiceImpl customerService = new CustomerServiceImpl(customerRepository);
        when(customerRepository.save(customerEntityToBeSaved)).thenReturn(savedCustomerEntity);

        //when
        Customer actualSavedCustomer = customerService.createCustomer(customerToBeSaved);

        //then
        verify(customerRepository).save(customerEntityToBeSaved); // did the customer repository save method get called with this entity
        assertEquals(expectedSavedCustomer, actualSavedCustomer);
    }

    @Test
    @DisplayName("give valid id, when the getCustomer method is called then it returns the customer with the same id")
    public void getCustomer() {
        //given
        Customer expectedCustomer = new Customer(2L, "Firstname", "SecondName", "email@email.com", 20);
        CustomerEntity savedCustomerEntity = new CustomerEntity(2L, "Firstname", "SecondName", "email@email.com", 20);
        CustomerServiceImpl customerService = new CustomerServiceImpl(customerRepository);
        when(customerRepository.findById(2L)).thenReturn(Optional.of(savedCustomerEntity));

        //when
        Customer actualCustomer = customerService.getCustomer(2L);

        //then
        assertEquals(expectedCustomer, actualCustomer);
    }

    @Test
    @DisplayName("given request for all customers, when get customers method is called, a full list of customers is returned")
    public void getCustomers() {
//        given

        CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository);
        List<Customer> expectedCustomerList = List.of(
                new Customer(1L, "Squall", "Leonhart", "test@test.com", 17),
                new Customer(2L, "Cloud", "Strife", "test@test.com", 24),
                new Customer(3L, "Noctis", "Caelum", "test@test.com", 20)
        );

//        when
        when(customerRepository.findAll()).thenReturn(List.of(new CustomerEntity(1L, "Squall", "Leonhart", "test@test.com", 17),
                new CustomerEntity(2L, "Cloud", "Strife", "test@test.com", 24),
                new CustomerEntity(3L, "Noctis", "Caelum", "test@test.com", 20)));

        List<Customer> actualCustomerList = customerServiceImpl.getCustomers();

//        then

        assertEquals(expectedCustomerList, actualCustomerList);
    }

    @Test
    @DisplayName("given valide customer when updated then customer in returned is the updated customer")
    public void updateCustomer() {
//        given
        CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository);
        Customer updateCustomerInput = new Customer(null, "updated", "customer", "updated@email.com", 20);
        CustomerEntity updateForCustomer = new CustomerEntity(null, "updated", "customer", "updated@email.com", 20);
        CustomerEntity updatedCustomer = new CustomerEntity(1L, "updated", "customer", "updated@email.com", 20);
        Customer expectedCustomer = new Customer(1L, "updated", "customer", "updated@email.com", 20);

//        when
        when(customerRepository.save(updateForCustomer)).thenReturn(updatedCustomer);
        Customer actualCustomer = customerServiceImpl.updateCustomer(updateCustomerInput);

//        then
        assertEquals(expectedCustomer, actualCustomer);
    }

    @Test
    @DisplayName("given valid customer fields when patchCustomer is called then the correct changes are made to the Customer")
    public void patchCustomer() {
//        given
        CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository);
        Customer patchCustomerInput = new Customer(1L, "patchedname", null, null, 0);
        CustomerEntity savedCustomer = new CustomerEntity(1L, "fistname", "lastname", "email@email.com", 1);
        CustomerEntity updatedCustomer = new CustomerEntity(1L, "patchedname", "lastname", "email@email.com", 1);
        Customer expectedCustomer = new Customer(1L, "patchedname", "lastname", "email@email.com", 1);

//        when
        when(customerRepository.findById(1L)).thenReturn(Optional.of(savedCustomer));
        when(customerRepository.save(updatedCustomer)).thenReturn(updatedCustomer);

        Customer actualCustomer = customerServiceImpl.patchCustomer(patchCustomerInput);
//        then
        assertEquals(expectedCustomer, actualCustomer);
    }

    @Test
    @DisplayName("given valid id, when deleted then doesn't return a value")
    public void deleteCustomer(){
//        given
        CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository);
        Customer saveCustomer = new Customer(1L, "delete", "customer", "email@email.com", 1);
        CustomerEntity saveCustomerEntity = new CustomerEntity(1L, "delete", "customer", "email@email.com", 1);
        CustomerEntity savedCustomer = new CustomerEntity(1L, "delete", "customer", "email@email.com", 1);

//        when
        String response = customerServiceImpl.deleteCustomer(1L);

//        then
        assertEquals("Success", response);

    }
}