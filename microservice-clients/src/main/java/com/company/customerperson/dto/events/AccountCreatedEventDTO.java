package com.company.customerperson.dto.events;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountCreatedEventDTO {
    private Long accountId;
    private String accountNumber;
    private Long customerId;
    private BigDecimal initialBalance;


    @Override
    public String toString() {
        return "AccountCreatedEventDTO{" +
                "accountId=" + accountId +
                ", accountNumber='" + accountNumber + "\''" +
                ", customerId=" + customerId +
                ", initialBalance=" + initialBalance +
                '}';
    }
}