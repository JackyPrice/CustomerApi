package com.coaching.customerapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.*;

@Entity
@Table(name = "Customers")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
}
