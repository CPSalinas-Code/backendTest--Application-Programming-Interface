package com.company.accountsmovements.service;

import com.company.accountsmovements.dto.MovementCreateDTO;
import com.company.accountsmovements.dto.MovementDTO;

import java.time.LocalDate;
import java.util.List;

public interface MovementService {

    /**
     * Crea un nuevo movimiento y actualiza el saldo de la cuenta.
     * @param movementCreateDTO DTO con datos para la creación del movimiento.
     * @return El DTO del movimiento creado.
     */
    MovementDTO createMovement(MovementCreateDTO movementCreateDTO);

    /**
     * Obtiene todos los movimientos de una cuenta en un rango de fechas.
     * @param accountId El ID de la cuenta.
     * @param startDate La fecha de inicio del reporte.
     * @param endDate La fecha de fin del reporte.
     * @return Una lista de movimientos.
     */
    List<MovementDTO> getMovementsByAccountAndDateRange(Long accountId, LocalDate startDate, LocalDate endDate);

    /**
     * Actualiza un movimiento existente.
     * NOTA: Esta operación puede ser peligrosa en un sistema financiero real.
     * @param id El ID del movimiento a actualizar.
     * @param movementDTO El DTO con los nuevos datos.
     * @return El DTO del movimiento actualizado.
     */
    MovementDTO updateMovement(Long id, MovementDTO movementDTO);

    /**
     * Elimina un movimiento existente.
     * NOTA: Esta operación puede ser peligrosa en un sistema financiero real.
     * @param id El ID del movimiento a eliminar.
     */
    void deleteMovement(Long id);
}