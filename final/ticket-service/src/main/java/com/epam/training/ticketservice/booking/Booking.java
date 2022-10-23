package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.screening.Screening;


import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"username","screening_movie_title","screening_room_name","screening_start_time"})})
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "screening_movie_title", referencedColumnName = "movie_title"),
            @JoinColumn(name = "screening_room_name", referencedColumnName = "room_name"),
            @JoinColumn(name = "screening_start_time", referencedColumnName = "start_time")
    })
    private Screening screeningg;

    public Booking() {

    }

    public Booking(Long id, String username, Screening screeningg, Set<Seat> seats) {
        this.id = id;
        this.username = username;
        this.screeningg = screeningg;
        this.seats = seats;
    }

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "seat_id", referencedColumnName = "id")
    private Set<Seat> seats;

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Screening getScreeningg() {
        return screeningg;
    }

    public void setScreeningg(Screening screeningg) {
        this.screeningg = screeningg;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", screeningg=" + screeningg +
                ", seats=" + seats +
                '}';
    }
}

