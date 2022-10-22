package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.screening.ScreeningId;
import com.epam.training.ticketservice.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@IdClass(BookingId.class)
public class Booking {


    @Id
    private String username;
    @Id
    //@OneToOne(targetEntity = ScreeningId.class)
    @JoinColumns({
            @JoinColumn(name = "screening_movie_title", referencedColumnName = "movieTitle"),
            @JoinColumn(name = "screening_room_name", referencedColumnName = "room_name"),
            @JoinColumn(name = "screening_start_time", referencedColumnName = "startTime")
    })
    private ScreeningId screening;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "booking",cascade = CascadeType.ALL)
    private Set<Seat> seats;

    @Override
    public String toString() {
        return "Booking{" +
                "username='" + username + '\'' +
                ", screening=" + screening +
                ", seats=" + seats +
                '}';
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

    public Booking(String username, ScreeningId screening) {
        this.username = username;
        this.screening = screening;
    }

    public Booking() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ScreeningId getScreeningId() {
        return screening;
    }

    public void setScreeningId(ScreeningId screening) {
        this.screening = screening;
    }

}

