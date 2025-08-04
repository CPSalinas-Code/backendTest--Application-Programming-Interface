package com.company.accountsmovements.service;

import com.company.accountsmovements.dto.StatementOfAccountDTO;

import java.time.LocalDate;

public interface ReportService {
    StatementOfAccountDTO generateStatementOfAccount(Long customerId, LocalDate startDate, LocalDate endDate);
}