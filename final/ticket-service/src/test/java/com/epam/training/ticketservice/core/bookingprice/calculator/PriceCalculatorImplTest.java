package com.epam.training.ticketservice.core.bookingprice.calculator;

import com.epam.training.ticketservice.core.bookingprice.BookingPrice;
import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.pricecomponent.PriceComponent;
import com.epam.training.ticketservice.core.room.Room;
import com.epam.training.ticketservice.core.screening.Screening;
import com.epam.training.ticketservice.core.seat.Seat;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PriceCalculatorImplTest {
    private PriceCalculatorImpl underTest = new PriceCalculatorImpl();

    @Test
    void testCalculateTotalPrice() {
        //given
        Set<Seat> seats = Set.of(
                new Seat(1,1),
                new Seat(1,2)
        );
        Screening screening = new Screening();
        screening.setMovie(new Movie());
        screening.setRoom(new Room());
        screening.setBookingPrice(new BookingPrice());
        Long expected = 3000L;

        //when
        Long actual = underTest.calculateTotalPrice(seats, screening);

        //then
        assertEquals(expected, actual);

    }

}