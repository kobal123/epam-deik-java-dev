package com.epam.training.ticketservice.screening;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.time.LocalDateTime;

@Entity
@IdClass(ScreeningId.class)
public class Screening {

    @Id
    private String movieTitle;
    @Id
    private String roomName;
    @Id
    private LocalDateTime startTime;


    public Screening(String movieTitle, String roomName, LocalDateTime startTime) {
        this.movieTitle = movieTitle;
        this.roomName = roomName;
        this.startTime = startTime;
    }

    public Screening(ScreeningId screeningId){
        this.movieTitle = screeningId.getMovieTitle();
        this.roomName = screeningId.getRoomName();
        this.startTime = screeningId.getStartTime();
    }

    public Screening() {

    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getRoomName() {
        return roomName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
