package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.screening.Screening;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"screening_movie_title",
        "screening_room_name",
        "screening_start_time",
        "seatRow",
        "seatCol"})})
public class Seat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer seatRow;
    private Integer seatCol;

    @ManyToOne
/*    @JoinColumns({
            @JoinColumn(name = "username", referencedColumnName = "username"),
            @JoinColumn(name =  "movie_title", referencedColumnName = "screening_movie_title"),
            @JoinColumn(name = "room_name", referencedColumnName = "screening_room_name"),
            @JoinColumn(name = "start_time", referencedColumnName = "screening_start_time")
    })*/
    private Booking booking;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "screening_movie_title", referencedColumnName = "movie_title"),
            @JoinColumn(name = "screening_room_name", referencedColumnName = "room_name"),
            @JoinColumn(name = "screening_start_time", referencedColumnName = "start_time")
    })
    private Screening screening;

    public Seat() {

    }

    public Seat(Long id, Integer seatRow, Integer seatCol, Booking booking, Screening screening) {
        this.id = id;
        this.seatRow = seatRow;
        this.seatCol = seatCol;
        this.booking = booking;
        this.screening = screening;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(Integer seatRow) {
        this.seatRow = seatRow;
    }

    public Integer getSeatCol() {
        return seatCol;
    }

    public void setSeatCol(Integer seatCol) {
        this.seatCol = seatCol;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", seatRow=" + seatRow +
                ", seatCol=" + seatCol +
                ", screening=" + screening +
                '}';
    }
}
