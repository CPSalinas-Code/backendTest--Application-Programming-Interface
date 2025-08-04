package com.company.accountsmovements.controller;

import com.company.accountsmovements.dto.AccountDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) // Levanta el contexto de Spring test
@AutoConfigureMockMvc // Configura MockMvc para hacer las peticiones HTTP
@AutoConfigureWireMock(port = 0) // Inicia un servidor WireMock en un puerto aleatorio
@ActiveProfiles("test")
class AccountControllerIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(AccountControllerIntegrationTest.class);

    @Autowired
    private MockMvc mockMvc; // simular las llamadas a nuestros endpoints

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenPostAccount_withValidCustomer_shouldCreateAccount() throws Exception {
        log.info("--- ARRANGE: Configurando la prueba de integración ---");
        // Simular la respuesta del microservicio de clientes con WireMock
        log.info("WireMock para simular el endpoint /api/clientes/1");
        stubFor(get(urlEqualTo("/api/clientes/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")));

        //Crear el cuerpo de la petición
        log.info("DTO para el cuerpo de la petición POST");
        AccountDTO newAccount = new AccountDTO();
        newAccount.setAccountNumber("INTEGRATION-TEST-ACCOUNT-001");
        newAccount.setAccountType("Ahorros");
        newAccount.setInitialBalance(new BigDecimal("500"));
        newAccount.setStatus(true);
        newAccount.setCustomerId(1L);

        // Act y Assert
        // Llamada POST a nuestro endpoint y verificamos la respuesta
        log.info("--- ACT & ASSERT: Ejecutando la petición y verificando la respuesta");
        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newAccount)))
                .andExpect(status().isCreated()) // estado 201 creado
                .andExpect(jsonPath("$.id").exists()) // el campo id debe existir
                .andExpect(jsonPath("$.accountNumber").value("INTEGRATION-TEST-ACCOUNT-001")); // Y que el nro de cuenta coincidan

        log.info("Prueba de integración finalizada con éxito: Se recibió 201 Created y el cuerpo de la respuesta es correcto.");

    }
}