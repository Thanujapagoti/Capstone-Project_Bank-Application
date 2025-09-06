package com.bank.statement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatementResponseDTO {
    private Long accountId;
    private LocalDate from;
    private LocalDate to;
    private List<TransactionDTO> transactions;
}
