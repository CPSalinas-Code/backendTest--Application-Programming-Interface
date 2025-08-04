package com.company.customerperson.dto;

import lombok.Data;

@Data
public class CustomerDTO extends PersonDTO {
    private String customerId;
    private String password;
    private Boolean status;
}