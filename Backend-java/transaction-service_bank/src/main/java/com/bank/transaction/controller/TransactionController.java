package com.bank.transaction.controller;

import com.bank.transaction.dto.TransferRequest;
import com.bank.transaction.dto.TransactionResponse;
import com.bank.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

private final TransactionService transactionService;

@PostMapping("/transfer")
public ResponseEntity<TransactionResponse> transfer(@RequestBody @Valid TransferRequest request) {
TransactionResponse response = transactionService.transfer(request);
return ResponseEntity.ok(response);
}
}