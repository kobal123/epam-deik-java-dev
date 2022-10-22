package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.room.Room;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@IdClass(ScreeningId.class)
public class Screening implements Serializable {

    @Id
    private String movieTitle;
    @Id
    @Column(name = "room_name")
    private String roomName;
    @Id
    private LocalDateTime startTime;

/*
    @OneToOne
    @JoinColumn(name = "movieTitle",referencedColumnName = "name",updatable = false,insertable = false)
    private Movie movie;

    @OneToOne
    @JoinColumn(name = "room_name",referencedColumnName = "name",updatable = false,insertable = false)
    private Room room;
*/

    @Override
    public String toString() {
        return "Screening{" +
                "movieTitle='" + movieTitle + '\'' +
                ", roomName='" + roomName + '\'' +
                ", startTime=" + startTime +
                '}';
    }

    public Screening(String movieTitle, String roomName, LocalDateTime startTime) {
        this.movieTitle = movieTitle;
        this.roomName = roomName;
        this.startTime = startTime;
    }

    public Screening(ScreeningId screeningId) {
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
