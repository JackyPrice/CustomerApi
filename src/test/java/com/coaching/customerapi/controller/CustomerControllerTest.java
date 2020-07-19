package com.coaching.customerapi.controller;

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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class) // "I want to use Mocks/Mock Objects in this class"
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

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}