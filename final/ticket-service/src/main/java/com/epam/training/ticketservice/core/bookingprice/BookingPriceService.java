package com.epam.training.ticketservice.core.bookingprice;

import com.epam.training.ticketservice.core.screening.Screening;
import com.epam.training.ticketservice.core.seat.Seat;

import java.util.Set;

public interface BookingPriceService {
    void updateBasePrice(Long price);

    Long getBookingPrice(Screening screening, Set<Seat> seats);
}
