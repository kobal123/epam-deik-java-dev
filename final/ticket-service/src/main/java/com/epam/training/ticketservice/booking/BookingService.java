package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.screening.ScreeningDto;
import com.epam.training.ticketservice.seat.SeatDto;

import java.util.List;
import java.util.Set;

public interface BookingService {

    List<BookingDto> getBookingsByUsername(String username);

    BookingDto createBooking(ScreeningDto screening, Set<SeatDto> seats);

    List<BookingDto> getAllBookings();

    Long preCheckBookingPrice(ScreeningDto screening, Set<SeatDto> seats);
}
