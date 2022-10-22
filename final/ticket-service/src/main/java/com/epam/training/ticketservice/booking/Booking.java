package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@IdClass(BookingId.class)
public class Booking implements Serializable {


    @Id
    private String username;


    @Id
    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "movieTitle"),
            @JoinColumn(name = "roomName"),
            @JoinColumn(name = "startTime")
    })
    private Screening screeningg;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "booking",cascade = CascadeType.ALL)
    private Set<Seat> seats = new HashSet<>();
/*
    @OneToOne
    @JoinColumn(name = "username")
    private User user;
*/
    public Booking() {

    }

    public Booking( String username, Screening screeningg, Set<Seat> seats) {
        this.username = username;
        this.screeningg = screeningg;
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Booking{" +

                ", username='" + username + '\'' +
                ", screeningg=" + screeningg +
                ", seats=" + seats +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

    public Screening getScreeningg() {
        return screeningg;
    }

    public void setScreeningg(Screening screeningg) {
        this.screeningg = screeningg;
    }
}

