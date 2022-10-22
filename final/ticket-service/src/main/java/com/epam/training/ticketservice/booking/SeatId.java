package com.epam.training.ticketservice.booking;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

public class SeatId implements Serializable {
    private Integer seatRow;
    private Integer seatCol;
    private Booking booking;

    public SeatId() {

    }

    public SeatId(Integer seatRow, Integer seatCol, Booking booking) {
        this.seatRow = seatRow;
        this.seatCol = seatCol;
        this.booking = booking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatId seatId = (SeatId) o;
        return Objects.equals(seatRow, seatId.seatRow) && Objects.equals(seatCol, seatId.seatCol) && Objects.equals(booking, seatId.booking);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatRow, seatCol, booking);
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
}
