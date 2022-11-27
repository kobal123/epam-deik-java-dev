package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Objects;
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
    @JoinColumn(name = "seat_id", referencedColumnName = "id")
    private Set<Seat> seats;

    public Booking(User user, Screening screening) {
        this.user = user;
        this.screening = screening;
    }

    public Booking(User user, Screening screening, Set<Seat> seats) {
        this.user = user;
        this.screening = screening;
        this.seats = seats;
    }
}

