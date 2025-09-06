package com.bank.statement.service;

import com.bank.statement.dto.StatementResponseDTO;
import com.bank.statement.dto.TransactionDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatementService {

    private final RestTemplate restTemplate;

    public StatementService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public StatementResponseDTO getStatements(Long accountId, LocalDate from, LocalDate to) {

        String url = String.format(
            "http://transaction-service/transactions/account/%d?from=%s&to=%s",
            accountId, from, to
        );

        ResponseEntity<List<TransactionDTO>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<TransactionDTO>>() {}
        );

        List<TransactionDTO> transactions = response.getBody();
        if (transactions == null) {
            transactions = Collections.emptyList();
        }

        // Filter just in case
        transactions = transactions.stream()
                .filter(tx -> !tx.getTxDate().isBefore(from) && !tx.getTxDate().isAfter(to))
                .collect(Collectors.toList());

        return new StatementResponseDTO(accountId, from, to, transactions);
    }
}
