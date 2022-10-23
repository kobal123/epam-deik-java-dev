package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.screening.Screening;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Seat implements Serializable {

    @EmbeddedId
    private SeatId seatId;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name =  "screening_movie_title", referencedColumnName = "movie_title"),
            @JoinColumn(name = "screening_room_name", referencedColumnName = "room_name"),
            @JoinColumn(name = "screening_start_time", referencedColumnName = "start_time")
    })
    private Screening screening;

    public Seat() {

    }

    public Seat(SeatId seatId, Screening screening) {
        this.seatId = seatId;
        this.screening = screening;
    }

    public SeatId getSeatId() {
        return seatId;
    }

    public void setSeatId(SeatId seatId) {
        this.seatId = seatId;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }
}
