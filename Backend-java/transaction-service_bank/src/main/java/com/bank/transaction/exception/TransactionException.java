package com.bank.transaction.exception;


@SuppressWarnings("serial")
public class TransactionException extends RuntimeException {
public TransactionException(String message) {
super(message);
}
public TransactionException(String message, Throwable t) {
super(message, t);
}
}