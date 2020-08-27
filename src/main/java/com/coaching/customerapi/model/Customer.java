package com.coaching.customerapi.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
}
