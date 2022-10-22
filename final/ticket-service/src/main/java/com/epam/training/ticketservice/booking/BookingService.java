package com.epam.training.ticketservice.booking;

import java.util.List;

public interface BookingService {

    List<Booking> getBookingsByUsername(String username);

    void createBooking(Booking booking);

}
