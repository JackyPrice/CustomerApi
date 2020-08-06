// package com.coaching.customerapi.service;
//
// import com.coaching.customerapi.entity.CustomerEntity;
// import com.coaching.customerapi.model.Customer;
// import com.coaching.customerapi.repository.CustomerRepository;
// import lombok.Data;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
//
// import java.util.List;
// import java.util.Optional;
//
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;
//
//
// @Data
// @ExtendWith(MockitoExtension.class)
// class CustomerServiceImplTest {
//
//     @Mock
//     private CustomerRepository customerRepository;
//
//     private CustomerServiceImpl customerService;
//
//     @BeforeEach
//     public void setup() {
//         this.customerService = new CustomerServiceImpl(customerRepository);
//     }
//
//     @Test
//     @DisplayName("given a valid Customer when the create customer method is called then it saves this customer in the database and returns a valid customer")
//     public void createCustomer() {
//         // given
//
//         Customer customerToBeSaved = Customer.builder().firstName("TestFirstName").lastName("TestSecondName").email("test@test.com").age(30).build();
//         CustomerEntity customerEntityToBeSaved = CustomerEntity.builder().firstName("TestFirstName").lastName("TestSecondName").email("test@test.com").age(30).build();
//
//         CustomerEntity savedCustomerEntity = CustomerEntity.builder().id(1L).firstName("TestFirstName").lastName("TestSecondName").email("test@test.com").age(30).build();
//         Customer expectedSavedCustomer = Customer.builder().id(1L).firstName("TestFirstName").lastName("TestSecondName").email("test@test.com").age(30).build();
//
//         when(customerRepository.save(customerEntityToBeSaved)).thenReturn(savedCustomerEntity);
//
//         // when
//         Customer actualSavedCustomer = customerService.createCustomer(customerToBeSaved);
//
//         // then
//         verify(customerRepository).save(customerEntityToBeSaved); //  did the customer repository save method get called with this entity
//         assertEquals(expectedSavedCustomer, actualSavedCustomer);
//     }
//
//     @Test
//     @DisplayName("give valid id, when the getCustomer method is called then it returns the customer with the same id")
//     public void getCustomer() {
//         // given
//         CustomerEntity savedCustomerEntity = CustomerEntity.builder().id(2L).firstName("Firstname").lastName("SsdsddgdfgesfecondName").email("email@email.com").age(20).build();
//         Customer expectedCustomer = Customer.builder().id(2L).firstName("Firstname").lastName("SecondName").email("email@email.com").age(20).build();
//
//         when(customerRepository.findById(2L)).thenReturn(Optional.of(savedCustomerEntity));
//
//         // when
//         Customer actualCustomer = customerService.getCustomer(2L);
//
//         // then
//         assertEquals(expectedCustomer, actualCustomer);
//     }
//
//     @Test
//     @DisplayName("given request for all customers, when get customers method is called, a full list of customers is returned")
//     public void getCustomers() {
//         // given
//         List<Customer> expectedCustomerList = List.of(
//                 Customer.builder().id(1L).firstName("Squall").lastName("Leonhart").email("test@test.com").age(17).build(),
//                 Customer.builder().id(2L).firstName("Cloud").lastName("Strife").email("test@test.com").age(24).build(),
//                 Customer.builder().id(3L).firstName("Noctis").lastName("Caelum").email("test@test.com").age(20).build()
//         );
//
//         when(customerRepository.findAll()).thenReturn(List.of(
//                 CustomerEntity.builder().id(1L).firstName("Squall").lastName("Leonhart").email("test@test.com").age(17).build(),
//                 CustomerEntity.builder().id(2L).firstName("Cloud").lastName("Strife").email("test@test.com").age(24).build(),
//                 CustomerEntity.builder().id(3L).firstName("Noctis").lastName("Caelum").email("test@test.com").age(20).build()
//         ));
//
//         // when
//         List<Customer> actualCustomerList = customerService.getCustomers();
//
//         // then
//         assertEquals(expectedCustomerList, actualCustomerList);
//     }
//
//     @Test
//     @DisplayName("given valid customer when updated then customer in returned is the updated customer")
//     public void updateCustomer() {
//         // given
//         Customer updateCustomerInput = Customer.builder().firstName("updated").lastName("customer").email("updated@test.com").age(20).build();
//         CustomerEntity updateCustomerEntity = CustomerEntity.builder().firstName("updated").lastName("customer").email("updated@test.com").age(20).build();
//         CustomerEntity updatedCustomer = CustomerEntity.builder().id(1L).firstName("updated").lastName("customer").email("updated@test.com").age(20).build();
//         Customer expectedCustomer = Customer.builder().id(1L).firstName("updated").lastName("customer").email("updated@test.com").age(20).build();
//
//         when(customerRepository.save(updateCustomerEntity)).thenReturn(updatedCustomer);
//
//         // when
//         Customer actualCustomer = customerService.updateCustomer(updateCustomerInput);
//
//         // then
//         assertEquals(expectedCustomer, actualCustomer);
//     }
//
//     @Test
//     @DisplayName("given valid customer fields when patchCustomer is called then the correct changes are made to the Customer")
//     public void patchCustomer() {
//         // given
//         Customer patchCustomerInput = Customer.builder().id(1L).firstName("patchedname").build();
//         CustomerEntity savedCustomer = CustomerEntity.builder().id(1L).firstName("fistname").lastName("lastname").email("email@email.com").age(1).build();
//         CustomerEntity patchedCustomer = CustomerEntity.builder().id(1L).firstName("patchedname").lastName("lastname").email("email@email.com").age(1).build();
//         Customer expectedCustomer = Customer.builder().id(1L).firstName("patchedname").lastName("lastname").email("email@email.com").age(1).build();
//
//         when(customerRepository.findById(1L)).thenReturn(Optional.of(savedCustomer));
//         when(customerRepository.save(patchedCustomer)).thenReturn(patchedCustomer);
//
//         // when
//         Customer actualCustomer = customerService.patchCustomer(patchCustomerInput);
//
//         // then
//         assertEquals(expectedCustomer, actualCustomer);
//     }
//
//
//     @Test
//     @DisplayName("given valid id, when deleted then doesn't return a value")
//     public void deleteCustomer() {
//         // given
//
//         // when
//         customerService.deleteCustomer(1L);
//         // then
//         verify(customerRepository).deleteById(1L);
//     }
// }