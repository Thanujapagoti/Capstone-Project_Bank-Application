package com.bank.account.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;


@Data
public class CreateAccountRequest {
@NotBlank(message = "accountNumber is required")
private String accountNumber;


@NotNull(message = "customerId is required")
private Long customerId;


@NotBlank(message = "accountType is required")
private String accountType; // SAVINGS/CURRENT


@PositiveOrZero(message = "initialDeposit must be >= 0")
private double initialDeposit;
}