package com.epam.training.ticketservice.screening.exception;

public class ScreeningNotFoundException extends RuntimeException{
    public ScreeningNotFoundException(String message) {
        super(message);
    }

}
