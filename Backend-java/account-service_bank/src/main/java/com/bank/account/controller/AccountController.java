package com.bank.account.controller;

import com.bank.account.dto.AccountResponse;
import com.bank.account.dto.CreateAccountRequest;
import com.bank.account.dto.TransactionRequest;
import com.bank.account.entity.Account;
import com.bank.account.exception.ResourceNotFoundException;
import com.bank.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

private final AccountService accountService;

@PostMapping
public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
Account created = accountService.createAccount(request);
AccountResponse resp = map(created);
return ResponseEntity.status(HttpStatus.CREATED).body(resp);
}


@GetMapping("/{id}")
public ResponseEntity<AccountResponse> getById(@PathVariable Long id) {
Account account = accountService.getAccountById(id);
return ResponseEntity.ok(map(account));
}


@GetMapping("/{id}/balance")
public ResponseEntity<Double> getBalance(@PathVariable Long id) {
double balance = accountService.getBalance(id);
return ResponseEntity.ok(balance);
}


@PostMapping("/{id}/credit")
public ResponseEntity<AccountResponse> credit(@PathVariable Long id, @Valid @RequestBody TransactionRequest request) {
Account updated = accountService.credit(id, request.getAmount());
return ResponseEntity.ok(map(updated));
}


@PostMapping("/{id}/debit")
public ResponseEntity<AccountResponse> debit(@PathVariable Long id, @Valid @RequestBody TransactionRequest request) {
Account updated = accountService.debit(id, request.getAmount());
return ResponseEntity.ok(map(updated));
}

private AccountResponse map(Account a) {
    if (a == null) {
        throw new ResourceNotFoundException("Account is null while mapping response");
    }
    return AccountResponse.builder()
            .id(a.getId())
            .accountNumber(a.getAccountNumber())
            .customerId(a.getCustomerId())
            .accountType(a.getAccountType())
            .balance(a.getBalance())
            .active(a.isActive())
            .build();
}


}

