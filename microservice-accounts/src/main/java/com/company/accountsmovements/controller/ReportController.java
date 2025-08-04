package com.company.accountsmovements.controller;

import com.company.accountsmovements.dto.StatementOfAccountDTO;
import com.company.accountsmovements.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


/**
 * Controlador REST para peticiones http para el la generacion del reporte, al ser solo una peticion Get, es el unico metodo que tiene
 *
 * @autor Christian Paul Salinas
 * @version 1.0
 */

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping
    public ResponseEntity<StatementOfAccountDTO> getStatementOfAccount(
            @RequestParam Long customerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        StatementOfAccountDTO report = reportService.generateStatementOfAccount(customerId, startDate, endDate);
        return ResponseEntity.ok(report);
    }
}