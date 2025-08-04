package com.company.accountsmovements.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO que recibe datos para la creacion de movimientos
 *
 * @autor Christian Paul Salinas
 * @version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovementCreateDTO {

    @NotBlank(message = "El numero de cuenta es obligatorio")
    private String accountNumber;

    @NotBlank(message = "El tipo de movimiento es obligatorio")
    private String movementType; // ejm, "debito", "credito"

    @NotNull(message = "El valor no puede ser nulo")
    private BigDecimal value;
}
