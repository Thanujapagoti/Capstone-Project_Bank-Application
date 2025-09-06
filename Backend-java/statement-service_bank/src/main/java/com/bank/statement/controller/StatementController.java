package com.bank.statement.controller;

import com.bank.statement.dto.StatementResponseDTO;
import com.bank.statement.service.StatementService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/statements")
public class StatementController {

    private final StatementService statementService;

    public StatementController(StatementService statementService) {
        this.statementService = statementService;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<StatementResponseDTO> getStatements(
            @PathVariable Long accountId,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        StatementResponseDTO response = statementService.getStatements(accountId, from, to);
        return ResponseEntity.ok(response);
    }
}
