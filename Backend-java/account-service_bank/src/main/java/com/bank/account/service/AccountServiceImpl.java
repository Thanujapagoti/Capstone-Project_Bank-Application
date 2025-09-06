package com.bank.account.service;


import com.bank.account.dto.CreateAccountRequest;
import com.bank.account.entity.Account;
import com.bank.account.exception.InsufficientBalanceException;
import com.bank.account.exception.ResourceNotFoundException;
import com.bank.account.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {


private final AccountRepository accountRepository;


@Override
public Account createAccount(CreateAccountRequest request) {
// check uniqueness of account number
Optional<Account> existing = accountRepository.findByAccountNumber(request.getAccountNumber());
if (existing.isPresent()) {
throw new IllegalArgumentException("Account number already exists");
}


Account account = Account.builder()
.accountNumber(request.getAccountNumber())
.customerId(request.getCustomerId())
.accountType(request.getAccountType())
.balance(request.getInitialDeposit())
.active(true)
.build();


Account saved = accountRepository.save(account);
log.debug("Created account {}", saved.getId());
return saved;
}


@Override
public Account getAccountById(Long id) {
return accountRepository.findById(id)
.orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
}


@Override
public double getBalance(Long accountId) {
Account account = getAccountById(accountId);
return account.getBalance();
}

@Override
@Transactional
public Account credit(Long accountId, double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("Credit amount must be greater than 0");
    }

    Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));

    if (!account.isActive()) {
        throw new IllegalStateException("Account is not active");
    }

    account.setBalance(account.getBalance() + amount);
    return accountRepository.save(account);
}

@Override
@Transactional
public Account debit(Long accountId, double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("Debit amount must be greater than 0");
    }

    Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));

    if (!account.isActive()) {
        throw new IllegalStateException("Account is not active");
    }

    if (account.getBalance() < amount) {
        throw new InsufficientBalanceException("Insufficient balance. Current balance: " + account.getBalance());
    }

    account.setBalance(account.getBalance() - amount);
    return accountRepository.save(account);
}


}

