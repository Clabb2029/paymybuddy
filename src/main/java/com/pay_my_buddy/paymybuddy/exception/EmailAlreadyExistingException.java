package com.pay_my_buddy.paymybuddy.exception;

public class EmailAlreadyExistingException extends RuntimeException {
    public EmailAlreadyExistingException(String errorMessage) {
        super(errorMessage);
    }
}
