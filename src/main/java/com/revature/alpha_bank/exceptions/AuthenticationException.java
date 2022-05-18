package com.revature.alpha_bank.exceptions;


public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super(message);
    }
}

