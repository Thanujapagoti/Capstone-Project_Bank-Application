package com.bank.account.exception;


@SuppressWarnings("serial")
public class InsufficientBalanceException extends RuntimeException {
public InsufficientBalanceException(String message) {
super(message);
}
}