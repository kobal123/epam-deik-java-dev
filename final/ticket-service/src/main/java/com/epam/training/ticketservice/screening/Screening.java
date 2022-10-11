package com.epam.training.ticketservice.screening;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Screening {
    @Id
    private String movieTitle;
    private String roomName;
    private LocalDateTime startTime;

    public Screening(String movieTitle, String roomName, LocalDateTime startTime) {
        this.movieTitle = movieTitle;
        this.roomName = roomName;
        this.startTime = startTime;
    }


    public Screening() {

    }


}
