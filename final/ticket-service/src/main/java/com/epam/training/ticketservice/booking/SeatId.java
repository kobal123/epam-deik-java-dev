package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class SeatId implements Serializable {
    private String username;
    private Integer seatRow;
    private Integer seatCol;


    public SeatId() {

    }

    public SeatId(String username, Integer seatRow, Integer seatCol, ScreeningId screeningId) {
        this.username = username;
        this.seatRow = seatRow;
        this.seatCol = seatCol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatId seatId = (SeatId) o;
        return Objects.equals(username, seatId.username) && Objects.equals(seatRow, seatId.seatRow) && Objects.equals(seatCol, seatId.seatCol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, seatRow, seatCol);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
