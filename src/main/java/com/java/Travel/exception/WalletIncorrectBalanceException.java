package com.java.Travel.exception;

public class WalletIncorrectBalanceException extends RuntimeException {

    public WalletIncorrectBalanceException(String message) {
        super(message);
    }
}
