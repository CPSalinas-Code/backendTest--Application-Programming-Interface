package com.company.accountsmovements.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO para la comunicacion con el microservice-clients
 *
 * @autor Christian Paul Salinas
 * @version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private Long id;
    private String name;
}