package com.epam.training.ticketservice.screening;

import java.time.LocalDateTime;

public class Screening {
    private final String movieTitle;
    private final String roomName;
    private final LocalDateTime startTime;

    public Screening(String movieTitle, String roomName, LocalDateTime startTime) {
        this.movieTitle = movieTitle;
        this.roomName = roomName;
        this.startTime = startTime;
    }
}
