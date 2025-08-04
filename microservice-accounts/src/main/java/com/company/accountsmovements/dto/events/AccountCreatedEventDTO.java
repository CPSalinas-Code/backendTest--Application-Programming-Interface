package com.company.accountsmovements.dto.events;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;


/**
 * Este es el objeto que viajará a través de RabbitMQ
 *
 * @autor Christian Paul Salinas
 * @version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreatedEventDTO {
    private Long accountId;
    private String accountNumber;
    private Long customerId;
    private BigDecimal initialBalance;
}