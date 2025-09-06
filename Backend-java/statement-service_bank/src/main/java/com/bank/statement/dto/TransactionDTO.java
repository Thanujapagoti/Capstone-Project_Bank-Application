package com.bank.statement.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionDTO {
    private String txId;
    private Long accountId;
    private String txType; // Credit/Debit
    private Double amount;
    private LocalDate txDate;
    private String description;
}
