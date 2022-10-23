package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningId;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;



@Embeddable
public class BookingId implements Serializable {
    private String username;
    private ScreeningId screeningId;

    public BookingId() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ScreeningId getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(ScreeningId screening) {
        this.screeningId = screening;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingId bookingId = (BookingId) o;
        return Objects.equals(username, bookingId.username) && Objects.equals(screeningId, bookingId.screeningId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, screeningId);
    }

    public BookingId(String username, ScreeningId screeningId) {
        this.username = username;
        this.screeningId = screeningId;
    }
}

