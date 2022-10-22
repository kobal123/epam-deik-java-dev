package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.screening.ScreeningId;

import java.io.Serializable;
import java.util.Objects;




public class BookingId implements Serializable {
    private String username;
    private ScreeningId screening;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ScreeningId getScreeningId() {
        return screening;
    }

    public void setScreeningId(ScreeningId screening) {
        this.screening = screening;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingId bookingId = (BookingId) o;
        return Objects.equals(username, bookingId.username) && Objects.equals(screening, bookingId.screening);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, screening);
    }
}

