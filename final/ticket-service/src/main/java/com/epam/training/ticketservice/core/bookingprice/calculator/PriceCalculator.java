package com.epam.training.ticketservice.core.bookingprice.calculator;

import com.epam.training.ticketservice.core.screening.Screening;
import com.epam.training.ticketservice.core.seat.Seat;

import java.util.Set;

public interface PriceCalculator {
    Long calculateTotalPrice(Set<Seat> seats, Screening screening);

}
