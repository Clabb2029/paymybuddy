package com.pay_my_buddy.paymybuddy.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String errorMessage) {
        super(errorMessage);
    }
}
