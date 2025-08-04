package com.company.accountsmovements.exception.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Clase que permite modelar mensajes de respuesta cuando ocurre un error.
 *
 * @autor Christian Paul Salinas
 * @version 1.0
 */

@Data
public class ApiError {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public ApiError(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

}