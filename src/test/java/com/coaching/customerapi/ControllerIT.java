package com.coaching.customerapi;

import com.coaching.customerapi.controller.CustomerController;
import com.coaching.customerapi.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=validate")
public class ControllerIT {

    @LocalServerPort
    private int port;

    private CustomerController customerController;


    // private final String customerId = "57ad00c4-5e08-43ca-9630-af962b7580ba";
    // private final String customerId2 = "e579e2d7-695c-43bb-a0b8-bf8bdd8036bd";

    private String customerId;
    private String customerId2;
    private WebClient webClient;

    @BeforeEach
    void setUp(){
        Customer testSave = Customer.builder()
                .firstName("Test")
                .lastName("Customer")
                .email("test@test.com")
                .age(1).build();

        Customer testSave2 = Customer.builder()
                .firstName("Test2")
                .lastName("Customer2")
                .email("test2@test.com")
                .age(2).build();

        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:" + port)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        this.customerId = webClient.post().uri("/api/customers")
                .body(BodyInserters.fromValue(testSave))
                .exchange()
                .block()
                .bodyToMono(Customer.class)
                .block()
                .getId();

                this.customerId2 = webClient.post().uri("/api/customers")
                .body(BodyInserters.fromValue(testSave2))
                .exchange()
                .block()
                .bodyToMono(Customer.class)
                .block()
                .getId();
    }


    @Test
    @DisplayName("given valid Customer body, when POST Request is made then that customer is created")
    void createCustomer() {
        Customer customerToSave = Customer.builder()
                .firstName("Test")
                .lastName("Customer")
                .email("test@test.com")
                .age(1).build();

        Customer response = webClient.post()
                .uri("api/customers")
                .body(BodyInserters.fromValue(customerToSave))
                .exchange()
                .block()
                .bodyToMono(Customer.class)
                .block();

        Customer expectedResponse = Customer.builder()
                .id(response.getId())
                .firstName("Test")
                .lastName("Customer")
                .email("test@test.com")
                .age(1).build();

        assertThat(response).isEqualTo(expectedResponse);
    }
    //
    // @Test
    // @DisplayName("given Get Request is made to customers endpoint then all customers are returned")
    // void getAllCustomers() {
    //     String expectedResponse = "[{" +
    //             "\"id\":\"" + customerId + "\"," +
    //             "\"firstName\":\"Test\"," +
    //             "\"lastName\":\"Customer\"," +
    //             "\"email\":\"test@test.com\"," +
    //             "\"age\":1" +
    //             "},{" +
    //             "\"id\":\"" + customerId2 + "\"," +
    //             "\"firstName\":\"Test2\"," +
    //             "\"lastName\":\"Customer2\"," +
    //             "\"email\":\"test2@test.com\"," +
    //             "\"age\":2" +
    //             "}]";
    //
    //     String response = webClient.get()
    //             .uri("api/customers")
    //             .accept(MediaType.APPLICATION_JSON)
    //             .retrieve()
    //             .bodyToMono(String.class)
    //             .block();
    //
    //     assertThat(response).isEqualTo(expectedResponse);
    // }

    @Test
    @DisplayName("given existing cusotmer ID when Get Request is made then that customer is returned")
    void getCustomer() {
        String expectedResponse = "{" +
                "\"id\":\"" + customerId + "\"," +
                "\"firstName\":\"Test\"," +
                "\"lastName\":\"Customer\"," +
                "\"email\":\"test@test.com\"," +
                "\"age\":1" +
                "}";

        String response = webClient.get()
                .uri(uriBuilder-> uriBuilder.path("api/customer/{id}").build(customerId))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        assertThat(response).isEqualTo(expectedResponse);
    }


    @Test
    @DisplayName("given valid Patch Request is made then customer is returned with expected changes")
    void patchCustomer() {
        String expectedResponse = "{" +
                "\"id\":\"" + customerId + "\"," +
                "\"firstName\":\"Patch\"," +
                "\"lastName\":\"Customer\"," +
                "\"email\":\"patch@test.com\"," +
                "\"age\":1" +
                "}";
        Customer patchCustomer = Customer.builder()
                .id(customerId)
                .firstName("Patch")
                .lastName("Customer")
                .email("patch@test.com")
                .age(1).build();

        String response = webClient.patch()
                .uri("api/customers")
                .body(BodyInserters.fromValue(patchCustomer))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("given valid Put Request is made then updated Customer is returned")
    void updateCustomer() {
        String expectedResponse = "{" +
                "\"id\":\"" + customerId + "\"," +
                "\"firstName\":\"Update\"," +
                "\"lastName\":\"Update\"," +
                "\"email\":\"update@test.com\"," +
                "\"age\":10" +
                "}";
        Customer patchCustomer = Customer.builder()
                .id(customerId)
                .firstName("Update")
                .lastName("Update")
                .email("update@test.com")
                .age(10).build();

        String response = webClient.put()
                .uri("api/customers")
                .body(BodyInserters.fromValue(patchCustomer))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("given valid Put Request is made then updated Customer is returned")
    void deleteCustomer() {
        String expectedResponse = null;

        String response = webClient.delete()
                .uri(uriBuilder -> uriBuilder.path("api/customers/{id}").build(customerId))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        assertThat(response).isEqualTo(expectedResponse);
    }
}
