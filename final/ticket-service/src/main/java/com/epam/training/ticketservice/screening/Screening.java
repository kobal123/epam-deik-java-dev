package com.epam.training.ticketservice.screening;


import com.epam.training.ticketservice.booking.Booking;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Screening implements Serializable {

    @EmbeddedId
    private ScreeningId screeningId = new ScreeningId();


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "screening")
    private List<Booking> bookings = new ArrayList<>();

    public Screening(ScreeningId screeningId) {
        this.screeningId = screeningId;
    }


    public ScreeningId getScreeningId() {
        return screeningId;
    }



    public String getMovieTitle() {
        return screeningId.getMovieTitle();
    }

    public String getRoomName() {
        return screeningId.getRoomName();
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public LocalDateTime getStartTime() {
        return screeningId.getStartTime();
    }

    public void setMovieTitle(String movieTitle) {
        this.screeningId.setMovieTitle(movieTitle);
    }


    public void setRoomName(String roomName) {
        this.screeningId.setRoomName(roomName);
    }

    public void setStartTime(LocalDateTime startTime) {
        this.screeningId.setStartTime(startTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Screening screening = (Screening) o;
        return Objects.equals(screeningId, screening.screeningId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(screeningId);
    }

    @Override
    public String toString() {
        return "Screening{" +
                "screeningId=" + screeningId +
                ", bookings=" + bookings +
                '}';
    }
}
