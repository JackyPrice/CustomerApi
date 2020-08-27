package com.coaching.customerapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {

    // @GenericGenerator(
    //         name = "UUID",
    //         strategy = "org.hibernate.id.UUIDGenerator",
    //         parameters = {
    //                 @Parameter(
    //                         name = "uuid_gen_strategy_class",
    //                         value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
    //                 )
    //         }
    // )
    @Id
    // @GeneratedValue(generator = "UUID")
    // @GenericGenerator(name = "uuid2", strategy = "uuid2")
    // @Column(columnDefinition = "BINARY(16)")
    // private UUID id;
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
}
