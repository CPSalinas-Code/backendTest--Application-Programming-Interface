package com.company.accountsmovements.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * DTO que recibe datos para el crud de movimientos
 *
 * @autor Christian Paul Salinas
 * @version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovementDTO {

    private Long id;
    private LocalDateTime date;
    private String movementType;
    private BigDecimal value;
    private BigDecimal balance;
    private Long accountId;
}
