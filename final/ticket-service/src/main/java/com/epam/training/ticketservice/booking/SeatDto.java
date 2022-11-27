package com.epam.training.ticketservice.booking;

import lombok.Value;

@Value
public class SeatDto {
    private Integer seatRow;
    private Integer seatCol;
}
