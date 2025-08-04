package com.company.accountsmovements.controller;

import com.company.accountsmovements.dto.MovementCreateDTO;
import com.company.accountsmovements.dto.MovementDTO;
import com.company.accountsmovements.service.MovementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador REST para gestionar las operaciones CRUD de los Movimientos.
 *
 * @author Christian Paul Salinas
 * @version 1.0
 */
@RestController
@RequestMapping("/api/movements")
public class MovementController {

    @Autowired
    private MovementService movementService;

    /**
     * Endpoint para crear un nuevo movimiento (crédito o débito).
     * @param movementCreateDTO Los datos del movimiento a crear.
     * @return El movimiento creado con su nuevo saldo y un estado 201 Created.
     */
    @PostMapping
    public ResponseEntity<MovementDTO> createMovement(@Valid @RequestBody MovementCreateDTO movementCreateDTO) {
        MovementDTO createdMovement = movementService.createMovement(movementCreateDTO);
        return new ResponseEntity<>(createdMovement, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MovementDTO>> getMovements(
            @RequestParam Long accountId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<MovementDTO> movements = movementService.getMovementsByAccountAndDateRange(accountId, startDate, endDate);
        return ResponseEntity.ok(movements);
    }

    /**
     * Endpoint para actualizar un movimiento existente.
     * @param id El ID del movimiento a actualizar.
     * @param movementDTO El DTO con los nuevos datos (solo se usarán type y date).
     * @return El movimiento actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MovementDTO> updateMovement(@PathVariable Long id, @Valid @RequestBody MovementDTO movementDTO) {
        MovementDTO updatedMovement = movementService.updateMovement(id, movementDTO);
        return ResponseEntity.ok(updatedMovement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovement(@PathVariable Long id) {
        movementService.deleteMovement(id);
        return ResponseEntity.noContent().build();
    }
}