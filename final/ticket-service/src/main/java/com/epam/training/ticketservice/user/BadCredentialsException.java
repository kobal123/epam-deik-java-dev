package com.epam.training.ticketservice.user;

public class BadCredentialsException extends RuntimeException {

    BadCredentialsException(String message) {
        super(message);
    }

    BadCredentialsException() {

    }
}
