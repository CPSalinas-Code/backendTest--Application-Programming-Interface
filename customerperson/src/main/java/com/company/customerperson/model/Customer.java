package com.company.customerperson.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Customer extends Person {

    @Column(name = "customer_id", nullable = false, unique = true)
    private String customerId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean status;
}