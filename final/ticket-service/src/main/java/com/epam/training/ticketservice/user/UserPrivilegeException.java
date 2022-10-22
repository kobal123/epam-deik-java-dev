package com.epam.training.ticketservice.user;

public class UserPrivilegeException extends RuntimeException {
    UserPrivilegeException(String message) {
        super(message);
    }

    UserPrivilegeException() {

    }
}
