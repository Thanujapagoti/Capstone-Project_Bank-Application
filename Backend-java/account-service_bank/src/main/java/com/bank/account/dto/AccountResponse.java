package com.bank.account.dto;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AccountResponse {
private Long id;
private String accountNumber;
private Long customerId;
private String accountType;
private double balance;
private boolean active;
}