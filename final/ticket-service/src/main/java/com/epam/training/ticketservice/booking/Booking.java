package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.screening.Screening;
import lombok.AllArgsConstructor;
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
import java.util.Objects;
import java.util.Set;

@Entity
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"username","screening_movie_title","screening_room_name","screening_start_time"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "screening_movie_title", referencedColumnName = "movie_title", nullable = false),
            @JoinColumn(name = "screening_room_name", referencedColumnName = "room_name",  nullable = false),
            @JoinColumn(name = "screening_start_time", referencedColumnName = "start_time",  nullable = false)
    })
    private Screening screening;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "seat_id", referencedColumnName = "id")
    private Set<Seat> seats;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id)
                && Objects.equals(username, booking.username)
                && Objects.equals(screening, booking.screening)
                && Objects.equals(seats, booking.seats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, screening, seats);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", screening=" + screening.getScreeningId() +
                ", seats=" + seats +
                '}';
    }
}

