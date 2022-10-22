package com.epam.training.ticketservice.booking;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
//@Table(name = "seat",uniqueConstraints = @UniqueConstraint(columnNames = {"seatRow", "seatCol","booking_id"}))
@IdClass(SeatId.class)
public class Seat implements Serializable {

    @Id
    private Integer seatRow;
    @Id
    private Integer seatCol;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "booking_username"),
            @JoinColumn(name = "booking_screening")
    })
    private Booking booking;

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    /*   @Id
       //@ManyToOne
       @JoinColumns({
           @JoinColumn(name = "username", referencedColumnName = "username"),
           @JoinColumn(name = "screening", referencedColumnName = "screening")
               //@JoinColumn(name = "movieTitle", referencedColumnName = "screening_movie_title"),
           //@JoinColumn(name = "roomName", referencedColumnName = "screening_room_name"),
           //@JoinColumn(name = "startTime", referencedColumnName = "screening_start_time")
       })
       private BookingId booking;
   */
    public Seat() {

    }


    @Override
    public String toString() {
        return "Seat{" +
                "seatRow=" + seatRow +
                ", seatCol=" + seatCol +
                '}';
    }

    public Seat(Integer seatRow, Integer seatCol) {
        this.seatRow = seatRow;
        this.seatCol = seatCol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return Objects.equals(seatRow, seat.seatRow) && Objects.equals(seatCol, seat.seatCol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatRow, seatCol);
    }

    public Integer getSeatRow() {
        return seatRow;
    }

    public Integer getSeatCol() {
        return seatCol;
    }

    public void setSeatRow(Integer seatRow) {
        this.seatRow = seatRow;
    }

    public void setSeatCol(Integer seatCol) {
        this.seatCol = seatCol;
    }
}
