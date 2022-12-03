package com.epam.training.ticketservice.core.booking;

import com.epam.training.ticketservice.core.screening.ScreeningDto;
import com.epam.training.ticketservice.core.seat.SeatDto;
import lombok.Value;
import java.util.Set;

@Value
public class BookingDto {
    ScreeningDto screeningDto;
    Set<SeatDto> seats;
    Long totalPrice;
}
