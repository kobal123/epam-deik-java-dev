package com.epam.training.ticketservice.screening;


import com.epam.training.ticketservice.bookingprice.BookingPrice;
import com.epam.training.ticketservice.booking.Booking;
import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.pricecomponent.PriceComponent;
import com.epam.training.ticketservice.room.Room;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"movie_id",
        "room_id",
        "start_time"})})
public class Screening implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(name = "movie_title")
    @OneToOne
    private Movie movie;

    //@Column(name = "room_name")
    @OneToOne
    private Room room;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @OneToOne
    private BookingPrice bookingPrice;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    private Set<PriceComponent> priceComponents = new HashSet<>();


    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public Screening(Movie movie, Room room, LocalDateTime startTime) {
        this.movie = movie;
        this.room = room;
        this.startTime = startTime;
    }

    void addPriceComponent(PriceComponent component) {
        priceComponents.add(component);
    }

    public Long getTicketPrice() {
        return bookingPrice.getPrice();
    }
}
