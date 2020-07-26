package com.coaching.customerapi.controller;

import com.coaching.customerapi.entity.CustomerEntity;
import com.coaching.customerapi.model.Customer;
import com.coaching.customerapi.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
        // "I want to use Mocks/Mock Objects in this class"
class CustomerControllerTest {
    @Mock
    private CustomerService customerService;
    @InjectMocks
    private CustomerController customerController;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    @DisplayName("given valid customer then return customer with valid id")
    void testCreateCustomer() throws Exception {
        //Given
        Customer customerToSave = new Customer();
        customerToSave.setFirstName("Test");
        customerToSave.setLastName("Customer");
        customerToSave.setAge(1);
        customerToSave.setEmail("test@test.com");

        Customer expectedCustomer = new Customer();
        expectedCustomer.setId(1L);
        expectedCustomer.setFirstName("Test");
        expectedCustomer.setLastName("Customer");
        expectedCustomer.setAge(1);
        expectedCustomer.setEmail("test@test.com");

        when(customerService.createCustomer(customerToSave)).thenReturn(expectedCustomer);
        //WhenThen
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/customers")
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerToSave))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Is.is(1)));
    }

    @Test
    @DisplayName("given valid id, when getCustomer get request is called then expected customer returned")
    void testGetCustomerById() throws Exception {
//        given
        Customer expectedCustomer = new Customer(1L, "carl", "saptarshi", "test@test.com", 23);
        when(customerService.getCustomer(1L)).thenReturn(expectedCustomer);

//        whenThen
        mockMvc.perform(get("/api/customer/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "\"id\": 1," +
                        "\"firstName\": \"carl\"," +
                        "\"lastName\": \"saptarshi\"," +
                        "\"email\": \"test@test.com\"," +
                        "\"age\": 23" +
                        "}"));
    }

    @Test
    @DisplayName("given request get request to customers endpoint, when called then expected list of customers returned")
    void testGetCustomersReturnsList() throws Exception {
//        given
        List<Customer> expectedCustomerList = List.of(
                new Customer(1L, "Jacky", "Price", "test@test.com", 31),
                new Customer(2L, "Carl", "Saptarshi", "test@test.com", 25)
        );

        when(customerService.getCustomers()).thenReturn(expectedCustomerList);

//        whenThen

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(content().json("[" +
                        "    {" +
                        "        \"id\": 1," +
                        "        \"firstName\": \"Jacky\"," +
                        "        \"lastName\": \"Price\"," +
                        "        \"email\": \"test@test.com\"," +
                        "        \"age\": 31" +
                        "    }," +
                        "    {" +
                        "        \"id\": 2," +
                        "        \"firstName\": \"Carl\"," +
                        "        \"lastName\": \"Saptarshi\"," +
                        "        \"email\": \"test@test.com\"," +
                        "        \"age\": 25" +
                        "    }" +
                        "]"
                ));
    }

    @Test
    @DisplayName("give valid input, when updateCustomer is called then updated customer is returned")
    void testUpdateCustomerWithValidInput() throws Exception {
//      given
        Customer updateCustomer = new Customer(2L, "updated", "Customer", "update@updateemail.com", 25);

//      whenthen
        when(customerService.updateCustomer(updateCustomer)).thenReturn(updateCustomer);


        mockMvc.perform(put("/api/customers")
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(updateCustomer))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "    \"id\": 2," +
                        "    \"firstName\": \"updated\"," +
                        "    \"lastName\": \"customer\"," +
                        "    \"email\": \"test@test.com\"," +
                        "    \"age\": 25" +
                        "}"));
    }

    @Test
    @DisplayName("given valid fields, when patchCustomer is called, then a Customer with these changes should be returned")
    void testPatchCustomerWithValidInputs() throws Exception {
//        given
        Customer patchForCustomer = new Customer(1L, "patch", null, null, 0);
        Customer patchedCustomer = new Customer(1L, "patch", "lastname", "test@test.com", 20);

        when(customerService.patchCustomer(patchForCustomer)).thenReturn(patchedCustomer);

//        whenthen
        mockMvc.perform(patch("/api/customers")
        .accept(MediaType.APPLICATION_JSON)
        .content(asJsonString(patchForCustomer))
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        .andExpect(content().json("{" +
                "    \"id\": 1," +
                "    \"firstName\": \"patch\"," +
                "    \"lastName\": \"lastname\"," +
                "    \"email\": \"test@test.com\"," +
                "    \"age\": 20" +
                "}"));
    }

    @Test
    @DisplayName("given valid id, when deleteCustomer is called then the customer is deleted")
    void testDeleteCustomerWithValidId() throws Exception {
//        given

//        whenThen
        mockMvc.perform(delete("/api/customers/1"))
                .andExpect(status().isOk());
        verify(customerService).deleteCustomer(1L);

    }

    //TODO: complete all

    //when doing delete, can't assert on void, but can verify the status is ok - all you can at this stage

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}