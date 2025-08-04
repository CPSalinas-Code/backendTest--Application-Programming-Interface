package com.company.accountsmovements.service;

import com.company.accountsmovements.dto.MovementCreateDTO;
import com.company.accountsmovements.dto.MovementDTO;
import com.company.accountsmovements.exception.InsufficientBalanceException;
import com.company.accountsmovements.model.Account;
import com.company.accountsmovements.model.Movement;
import com.company.accountsmovements.repository.AccountRepository;
import com.company.accountsmovements.repository.MovementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio definido para la logica del crud para Movimientos
 * Hay que tener en cuenta que los movimientos son o deberian ser datos inmutables y solo deberia existir el metodo crear Movimiento
 *
 * Pero se generan los demas metodos solo por motivo de demostracion del requerimiento.
 *
 * @autor Christian Paul Salinas
 * @version 1.0
 */



@Service
public class MovementServiceImpl implements MovementService {

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private AccountRepository accountRepository;


    /**
     * Metodo para la creacion de un nuevo registro de Movimiento
     * @param dto DTO con datos para la creación del movimiento.
     * @return Movimiento guardado
     */
    @Override
    @Transactional
    public synchronized MovementDTO createMovement(MovementCreateDTO dto) {

        Account account = accountRepository.findByAccountNumber(dto.getAccountNumber())
                .orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada, numero de cuenta: " + dto.getAccountNumber()));


        BigDecimal currentBalance = movementRepository.findTopByAccountIdOrderByDateDesc(account.getId())
                .map(Movement::getBalance)
                .orElse(account.getInitialBalance());

        BigDecimal newBalance = currentBalance.add(dto.getValue());

        //validacion de salgo negativo
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            //Si la cuenta no corriente se muestra el mensaje
            if (!"Corriente".equalsIgnoreCase(account.getAccountType())) {
                throw new InsufficientBalanceException("Saldo no disponible");
            }
            // Si la cuenta es cuenta corriente", permitimos el sobregiro y no lanzamos excepción.
        }
        Movement movement = new Movement();
        movement.setDate(LocalDateTime.now());
        movement.setMovementType(dto.getMovementType());
        movement.setValue(dto.getValue());
        movement.setBalance(newBalance);
        movement.setAccount(account);

        Movement savedMovement = movementRepository.save(movement);
        return toDTO(savedMovement);
    }

    /**
     *  Metodo para obtener los movimientos de una cuenta en un rango de fechas
     * @param accountId El ID de la cuenta.
     * @param startDate La fecha de inicio del reporte.
     * @param endDate La fecha de fin del reporte.
     * @return Movimientos encontrados
     */
    @Override
    @Transactional(readOnly = true)
    public List<MovementDTO> getMovementsByAccountAndDateRange(Long accountId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        return movementRepository.findByAccountIdAndDateBetweenOrderByDateAsc(accountId, startDateTime, endDateTime)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Metodo que modifica ciertos datos de registros de movimiento, segun la logica de negodcio
     * @param id El ID del movimiento a actualizar.
     * @param movementDTO El DTO con los nuevos datos.
     * @return Movimiento actualizado
     */
    @Override
    @Transactional
    public MovementDTO updateMovement(Long id, MovementDTO movementDTO) {
        Movement existingMovement = movementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movimiento no encontrado con el id: " + id));

        // Solo se permite actualizar campos informativos, no valores para no romper la logica
        // Puede modificarse segun la logica de negocio
        existingMovement.setMovementType(movementDTO.getMovementType());
        //existingMovement.setDate(movementDTO.getDate());

        Movement updatedMovement = movementRepository.save(existingMovement);
        return toDTO(updatedMovement);
    }

    /**
     * Metodo que elimina movimientos
     * @param id El ID del movimiento a eliminar.
     */
    @Override
    @Transactional
    public void deleteMovement(Long id) {
        Movement movementToDelete = movementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movimiento no encontrado con el id: " + id));

        // Regla de negocio: solo se puede borrar el último movimiento de la cuenta.
        Movement lastMovement = movementRepository.findTopByAccountIdOrderByDateDesc(movementToDelete.getAccount().getId())
                .orElse(null);

        if (lastMovement == null || !lastMovement.getId().equals(movementToDelete.getId())) {
            throw new IllegalArgumentException("Solo sa puede borrar el ultimo movimiento del registro");
        }

        movementRepository.delete(movementToDelete);
    }

    private MovementDTO toDTO(Movement movement) {
        return new MovementDTO(
                movement.getId(),
                movement.getDate(),
                movement.getMovementType(),
                movement.getValue(),
                movement.getBalance(),
                movement.getAccount().getId()
        );
    }
}
