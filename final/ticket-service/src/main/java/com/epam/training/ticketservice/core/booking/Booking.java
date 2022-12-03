package com.epam.training.ticketservice.core.booking;

import com.epam.training.ticketservice.core.seat.Seat;
import com.epam.training.ticketservice.core.screening.Screening;
import com.epam.training.ticketservice.core.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @ManyToOne
    private Screening screening;

    private Long priceTotal;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Seat> seats = new HashSet<>();


    public Booking(User user, Screening screening, Set<Seat> seats) {
        this.user = user;
        this.screening = screening;
        this.seats = seats;
    }
}

