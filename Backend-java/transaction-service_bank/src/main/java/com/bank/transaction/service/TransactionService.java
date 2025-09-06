package com.bank.transaction.service;

import com.bank.transaction.dto.TransferRequest;
import com.bank.transaction.dto.TransactionResponse;

public interface TransactionService {
    TransactionResponse transfer(TransferRequest request);
}
