package com.bank.account.service;


import com.bank.account.dto.CreateAccountRequest;
import com.bank.account.entity.Account;


public interface AccountService {
Account createAccount(CreateAccountRequest request);
Account getAccountById(Long id);
double getBalance(Long accountId);
Account credit(Long accountId, double amount);
Account debit(Long accountId, double amount);
}