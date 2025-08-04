package com.company.accountsmovements.repository;

import com.company.accountsmovements.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

    // Busca todos los movimientos de una cuenta dentro de un rango de fechas, para la generacion de reportes
    List<Movement> findByAccountIdAndDateBetweenOrderByDateAsc(Long accountId, LocalDateTime startDate, LocalDateTime endDate);

    // Busca el último movimiento de una cuenta para obtener el saldo más reciente.
    Optional<Movement> findTopByAccountIdOrderByDateDesc(Long accountId);
}
