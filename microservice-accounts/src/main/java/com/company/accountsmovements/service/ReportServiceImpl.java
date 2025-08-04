package com.company.accountsmovements.service;

import com.company.accountsmovements.dto.*;
import com.company.accountsmovements.model.Account;
import com.company.accountsmovements.repository.AccountRepository;
import com.company.accountsmovements.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Servicio para la lógica de negocio relacionada con la generación de reportes.
 * Orquesta llamadas a otros servicios y a repositorios locales.
 *
 *
 * @autor Christian Paul Salinas
 * @version 1.0
 */

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private WebClient webClient;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MovementRepository movementRepository;

    /**
     * Genera un estado de cuenta para un cliente específico en un rango de fechas.
     * @param customerId El ID del cliente para el que se genera el reporte.
     * @param startDate La fecha de inicio del reporte.
     * @param endDate La fecha de fin del reporte.
     * @return Un DTO que contiene la información completa del estado de cuenta.
     */
    @Override
    public StatementOfAccountDTO generateStatementOfAccount(Long customerId, LocalDate startDate, LocalDate endDate) {
        // Llama al servicio de clientes para obtener el nombre. La llamada es asíncrona.
        Mono<ClientDTO> clientMono = webClient.get()
                .uri("/clientes/" + customerId)
                .retrieve()
                .bodyToMono(ClientDTO.class)
                .onErrorResume(e -> Mono.just(new ClientDTO(customerId, "Cliente no encontrado")));

        List<Account> accounts = accountRepository.findAllByCustomerId(customerId);

        List<AccountReportDTO> accountReports = accounts.stream().map(account -> {
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

            List<MovementDTO> movements = movementRepository
                    .findByAccountIdAndDateBetweenOrderByDateAsc(account.getId(), startDateTime, endDateTime)
                    .stream()
                    .map(this::toMovementDTO)
                    .collect(Collectors.toList());

            return toAccountReportDTO(account, movements);
        }).collect(Collectors.toList());
        // Espera la respuesta del servicio de clientes para obtener el nombre.
        String clientName = clientMono.block().getName();

        return new StatementOfAccountDTO(clientName, accountReports);
    }

    private MovementDTO toMovementDTO(com.company.accountsmovements.model.Movement movement) {
        return new MovementDTO(
                movement.getId(),
                movement.getDate(),
                movement.getMovementType(),
                movement.getValue(),
                movement.getBalance(),
                movement.getAccount().getId()
        );
    }

    private AccountReportDTO toAccountReportDTO(Account account, List<MovementDTO> movements) {
        return new AccountReportDTO(
                account.getAccountNumber(),
                account.getAccountType(),
                account.getInitialBalance(),
                account.getStatus(),
                movements
        );
    }
}