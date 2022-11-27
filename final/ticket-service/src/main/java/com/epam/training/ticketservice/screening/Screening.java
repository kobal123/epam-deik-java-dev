package com.epam.training.ticketservice.screening;


import com.epam.training.ticketservice.booking.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"movie_title",
        "room_name",
        "start_time"})})
public class Screening implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_title")
    private String movieTitle;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    private Long ticketPrice = 1500L;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();



    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public Screening(String movieTitle, String roomName, LocalDateTime startTime) {
        this.movieTitle = movieTitle;
        this.roomName = roomName;
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "Screening{" +
                "id=" + id +
                ", movieTitle='" + movieTitle + '\'' +
                ", roomName='" + roomName + '\'' +
                ", startTime=" + startTime +
                '}';
    }
}
