package com.company.accountsmovements.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;


/**
 * DTO para la genearacion de datos en el reporte
 *
 * @autor Christian Paul Salinas
 * @version 1.0
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatementOfAccountDTO {
    private String clientName;
    private List<AccountReportDTO> accounts;
}