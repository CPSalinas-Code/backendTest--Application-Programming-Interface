package com.company.customerperson.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String gender;
    private Integer age;

    @Column(nullable = false, unique = true)
    private String identification;

    private String address;
    private String phone;
}