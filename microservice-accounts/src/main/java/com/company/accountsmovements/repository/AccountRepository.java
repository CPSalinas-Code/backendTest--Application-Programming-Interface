package com.company.accountsmovements.repository;

import com.company.accountsmovements.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // JPA crea automáticamente una consulta para buscar una cuenta por su número.
    // Es neceario ara evitar duplicados y para buscar cuentas fácilmente.
    Optional<Account> findByAccountNumber(String accountNumber);

    // Metodo para encontrar todas las cuentas de un cliente específico.
    List<Account> findAllByCustomerId(Long customerId);
}