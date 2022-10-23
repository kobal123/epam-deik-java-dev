package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.screening.Screening;


import javax.persistence.*;

@Entity
public class Booking {

    @EmbeddedId
    private BookingId bookingId;


    @OneToOne()
    @JoinColumns({
            @JoinColumn(name = "screening_movie_title", referencedColumnName = "movie_title"),
            @JoinColumn(name = "screening_room_name", referencedColumnName = "room_name"),
            @JoinColumn(name = "screening_start_time", referencedColumnName = "start_time")
    })
    private Screening screeningg;



    @Override
    public String toString() {
        return "Booking{" +
                "username='" + bookingId.getUsername() + '\'' +
                ", screening=" + screeningg +
                '}';
    }


    public Booking() {

    }

    public Booking(BookingId bookingId, Screening screeningg) {
        this.bookingId = bookingId;
        this.screeningg = screeningg;
    }

    public BookingId getBookingId() {
        return bookingId;
    }

    public void setBookingId(BookingId bookingId) {
        this.bookingId = bookingId;
    }

    public Screening getScreeningg() {
        return screeningg;
    }

    public void setScreeningg(Screening screening) {
        this.screeningg = screening;
    }

}

