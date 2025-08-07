package com.company.apigateway.web;

import com.company.apigateway.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *  Controlador para el endpoint de peticion para generar el Token de prueba
 *
 * @author Christian Paul Salinas
 * @version 1.0
 */

@RestController
public class TokenController {

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/generate-token")
    public String generateToken(@RequestParam(defaultValue = "test-user") String username) {
        return jwtUtil.generateToken(username);
    }
}
