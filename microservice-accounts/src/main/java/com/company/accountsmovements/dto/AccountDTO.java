package com.company.accountsmovements.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO que funciona para los datos en el crud de Cuentas
 *
 * @autor Christian Paul Salinas
 * @version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private Long id;

    @NotBlank(message = "El numero de cuenta es obligatorio")
    private String accountNumber;

    @NotBlank(message = "El tipo de cuenta es obligatorio")
    private String accountType;

    @NotNull(message = "El balance inicial es obligatorio")
    private BigDecimal initialBalance;

    @NotNull(message = "El status es obligatorio")
    private Boolean status;

    @NotNull(message = "idCliente es obligatorio")
    private Long customerId;
}
