package com.company.accountsmovements.service;

import com.company.accountsmovements.config.RabbitMQConfig;
import com.company.accountsmovements.dto.AccountDTO;
import com.company.accountsmovements.dto.events.AccountCreatedEventDTO;
import com.company.accountsmovements.model.Account;
import com.company.accountsmovements.repository.AccountRepository;
import com.company.accountsmovements.restClients.ClientServiceFeignClient;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import feign.FeignException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio definido para la logica del crud para Cuentas.
 *
 * @autor Christian Paul Salinas
 * @version 1.0
 */


@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ClientServiceFeignClient clientServiceFeignClient;

    @Override
    @Transactional
    public AccountDTO createAccount(AccountDTO accountDTO) {

        // La validación de cliente hacia microservice-clients;
        validateCustomerExists(accountDTO.getCustomerId());

        accountRepository.findByAccountNumber(accountDTO.getAccountNumber()).ifPresent(a -> {
            throw new IllegalArgumentException("Numero de cuenta " + a.getAccountNumber() + " ya esta registrado");
        });

        Account account = toEntity(accountDTO);
        Account savedAccount = accountRepository.save(account);

        // --- INICIO DE LA LÓGICA ASÍNCRONA ---
        // Crear el evento DTO
        AccountCreatedEventDTO event = new AccountCreatedEventDTO(
                savedAccount.getId(),
                savedAccount.getAccountNumber(),
                savedAccount.getCustomerId(),
                savedAccount.getInitialBalance()
        );
        // Enviar el evento a RabbitMQ
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, event);
        // --- FIN DE LA LÓGICA ASÍNCRONA ---

        return toDTO(savedAccount);
    }

    private void validateCustomerExists(Long customerId) {
        try {
            clientServiceFeignClient.customerExists(customerId);
        } catch (FeignException.NotFound ex) {
            // Feign lanza una excepción específica para el error 404
            throw new EntityNotFoundException("No se encontro cliente con el siguiente id: " + customerId);
        } catch (Exception ex) {
            // Captura otros posibles errores (ej. servicio de cliente caído)
            throw new IllegalStateException("Error en la validacion del cliente con el id: " + customerId, ex);
        }
    }


    @Override
    public AccountDTO getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada con el id: " + id));
        return toDTO(account);
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AccountDTO updateAccount(Long id, AccountDTO accountDTO) {

        // La validación de cliente hacia microservice-clients;
        validateCustomerExists(accountDTO.getCustomerId());

        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada con el id: " + id));

        existingAccount.setAccountNumber(accountDTO.getAccountNumber());
        existingAccount.setAccountType(accountDTO.getAccountType());
        existingAccount.setInitialBalance(accountDTO.getInitialBalance());
        existingAccount.setStatus(accountDTO.getStatus());
        existingAccount.setCustomerId(accountDTO.getCustomerId());

        Account updatedAccount = accountRepository.save(existingAccount);
        return toDTO(updatedAccount);
    }

    @Override
    @Transactional
    public void deleteAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new EntityNotFoundException("Cuenta no encontrada con el id: " + id);
        }
        accountRepository.deleteById(id);
    }

    private AccountDTO toDTO(Account account) {
        return new AccountDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getInitialBalance(),
                account.getStatus(),
                account.getCustomerId()
        );
    }

    private Account toEntity(AccountDTO dto) {
        Account account = new Account();
        account.setId(dto.getId());
        account.setAccountNumber(dto.getAccountNumber());
        account.setAccountType(dto.getAccountType());
        account.setInitialBalance(dto.getInitialBalance());
        account.setStatus(dto.getStatus());
        account.setCustomerId(dto.getCustomerId());
        return account;
    }
}