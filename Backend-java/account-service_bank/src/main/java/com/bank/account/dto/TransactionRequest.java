package com.bank.account.dto;


import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TransactionRequest {
@Positive(message = "amount must be greater than 0")
private double amount;
}