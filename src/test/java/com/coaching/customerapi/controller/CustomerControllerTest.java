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

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
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

    private static final String TEST_UUID_ID_1 = "123e4567-e89b-12d3-a456-426614174000";
    private static final String TEST_UUID_ID_2 = "123e4567-e89b-12d3-a456-426614174001";

    @Test
    @DisplayName("given valid customer then return customer with valid id")
    void testCreateCustomer() throws Exception {
        // given
        Customer customerToSave = Customer.builder()
                .firstName("Test")
                .lastName("Customer")
                .email("test@test.com")
                .age(1).build();

        Customer expectedCustomer = Customer.builder()
        .id(TEST_UUID_ID_1)
        .firstName("Test")
        .lastName("Customer")
        .email("test@test.com")
        .age(1).build();

        when(customerService.createCustomer(customerToSave)).thenReturn(expectedCustomer);
        // WhenThen
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/customers")
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerToSave))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Is.is("123e4567-e89b-12d3-a456-426614174000")));
    }

    @Test
    @DisplayName("given valid id, when getCustomer get request is called then expected customer returned")
    void testGetCustomerById() throws Exception {
      // given
        Customer expectedCustomer = Customer.builder()
        .id(TEST_UUID_ID_1)
        .firstName("carl")
        .lastName("saptarshi")
        .email("test@test.com")
        .age(23).build();

        when(customerService.getCustomer(TEST_UUID_ID_1)).thenReturn(expectedCustomer);

      // whenThen
        mockMvc.perform(get("/api/customer/"+ TEST_UUID_ID_1))
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "\"id\": 123e4567-e89b-12d3-a456-426614174000," +
                        "\"firstName\": \"carl\"," +
                        "\"lastName\": \"saptarshi\"," +
                        "\"email\": \"test@test.com\"," +
                        "\"age\": 23" +
                        "}"));
    }

    @Test
    @DisplayName("given request get request to customers endpoint, when called then expected list of customers returned")
    void testGetCustomersReturnsList() throws Exception {
        // given
        Customer customer1 = Customer.builder()
                .id(TEST_UUID_ID_1).firstName("Jacky")
                .lastName("Price")
                .email("test@test.com")
                .age(31)
                .build();

        Customer customer2 = Customer.builder()
                .id(TEST_UUID_ID_2)
                .firstName("Carl")
                .lastName("Saptarshi")
                .email("test@test.com")
                .age(25)
                .build();

        List<Customer> expectedCustomerList = List.of(customer1, customer2);

        when(customerService.getCustomers()).thenReturn(expectedCustomerList);

        // whenThen
        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(content().json("[" +
                        "    {" +
                        "        \"id\": 123e4567-e89b-12d3-a456-426614174000," +
                        "        \"firstName\": \"Jacky\"," +
                        "        \"lastName\": \"Price\"," +
                        "        \"email\": \"test@test.com\"," +
                        "        \"age\": 31" +
                        "    }," +
                        "    {" +
                        "        \"id\": 123e4567-e89b-12d3-a456-426614174001," +
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
        // given
        Customer updateCustomer = Customer.builder().id(TEST_UUID_ID_2).firstName("updated").lastName("customer").email("update@updateemail.com").age(25).build();

        // whenthen
        when(customerService.updateCustomer(updateCustomer)).thenReturn(updateCustomer);

        mockMvc.perform(put("/api/customers")
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(updateCustomer))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "    \"id\": 123e4567-e89b-12d3-a456-426614174001," +
                        "    \"firstName\": \"updated\"," +
                        "    \"lastName\": \"customer\"," +
                        "    \"email\": \"update@updateemail.com\"," +
                        "    \"age\": 25" +
                        "}"));
    }

    @Test
    @DisplayName("given valid fields, when patchCustomer is called, then a Customer with these changes should be returned")
    void testPatchCustomerWithValidInputs() throws Exception {
        // given
        Customer patchForCustomer = Customer.builder()
                .id(TEST_UUID_ID_1)
                .firstName("patch")
                .build();

        Customer patchedCustomer = Customer.builder()
                .id(TEST_UUID_ID_1)
                .firstName("patch")
                .lastName("lastName")
                .email("test@test.com")
                .age(20)
                .build();

        when(customerService.patchCustomer(patchForCustomer)).thenReturn(patchedCustomer);

        // whenthen
        mockMvc.perform(patch("/api/customers")
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(patchForCustomer))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "    \"id\": 123e4567-e89b-12d3-a456-426614174000," +
                        "    \"firstName\": \"patch\"," +
                        "    \"lastName\": \"lastName\"," +
                        "    \"email\": \"test@test.com\"," +
                        "    \"age\": 20" +
                        "}"));
    }

    @Test
    @DisplayName("given valid id, when deleteCustomer is called then the customer is deleted")
    void testDeleteCustomerWithValidId() throws Exception {
      // given

      // whenThen
        mockMvc.perform(delete("/api/customers/123e4567-e89b-12d3-a456-426614174000"))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomer(TEST_UUID_ID_1);

    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}