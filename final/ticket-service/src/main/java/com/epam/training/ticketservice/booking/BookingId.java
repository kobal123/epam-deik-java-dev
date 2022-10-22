package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.screening.Screening;

import java.io.Serializable;
import java.util.Objects;




public class BookingId implements Serializable {
    private String username;
    private Screening screeningg;


    public BookingId(String username, Screening screeningg) {

        this.username = username;
        this.screeningg = screeningg;
    }

    public BookingId() {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingId bookingId = (BookingId) o;
        return Objects.equals(username, bookingId.username) && Objects.equals(screeningg, bookingId.screeningg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, screeningg);
    }
}

