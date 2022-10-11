package com.epam.training.ticketservice.movie;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Movie {
    @Id
    private String name;
    private String genre;
    private int screenTimeInMinutes;

    public Movie(String name, String genre, int screenTimeInMinutes) {
        this.name = name;
        this.genre = genre;
        this.screenTimeInMinutes = screenTimeInMinutes;
    }

    public Movie() {

    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public int getScreenTimeInMinutes() {
        return screenTimeInMinutes;
    }
}
