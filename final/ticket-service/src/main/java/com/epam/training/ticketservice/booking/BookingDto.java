package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.screening.ScreeningDto;
import lombok.Value;
import java.util.Set;

@Value
public class BookingDto {
    ScreeningDto screeningDto;
    Set<SeatDto> seats;
    Long totalPrice;
}
