package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.booking.Seat;
import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.room.Room;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Screening implements Serializable {

    @EmbeddedId
    private ScreeningId screeningId;




    public Screening(ScreeningId screeningId) {
        this.screeningId = screeningId;
    }

    public ScreeningId getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(ScreeningId screeningId) {
        this.screeningId = screeningId;
    }

    @Override
    public String toString() {
        return "Screening{" +
                "screeningId=" + screeningId +
                '}';
    }


    public Screening() {

    }


    public String getMovieTitle() {
        return screeningId.getMovieTitle();
    }

    public String getRoomName() {
        return screeningId.getRoomName();
    }

    public LocalDateTime getStartTime() {
        return screeningId.getStartTime();
    }

    public void setMovieTitle(String movieTitle) {
        this.screeningId.setMovieTitle(movieTitle);
    }


    public void setRoomName(String roomName) {
        this.screeningId.setRoomName(roomName);
    }

    public void setStartTime(LocalDateTime startTime) {
        this.screeningId.setStartTime(startTime);
    }
}
