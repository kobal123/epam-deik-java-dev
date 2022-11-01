package com.epam.training.ticketservice.movie.exception;

public class MovieNotFoundException extends RuntimeException{

    public MovieNotFoundException(String message) {
        super(message);
    }

    public MovieNotFoundException() {

    }
}
