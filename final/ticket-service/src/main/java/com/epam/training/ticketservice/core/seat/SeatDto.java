package com.epam.training.ticketservice.core.seat;

import lombok.Value;

@Value
public class SeatDto {
    private Integer seatRow;
    private Integer seatCol;
}
