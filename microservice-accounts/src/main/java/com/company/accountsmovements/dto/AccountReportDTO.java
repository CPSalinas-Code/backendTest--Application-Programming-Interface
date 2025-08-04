package com.company.accountsmovements.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO que funciona para los datos en la generacion de reportes
 *
 * @autor Christian Paul Salinas
 * @version 1.0
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountReportDTO {
    private String accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private Boolean status;
    private List<MovementDTO> movements;
}