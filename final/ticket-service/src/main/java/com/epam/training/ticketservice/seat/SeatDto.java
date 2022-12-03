package com.epam.training.ticketservice.seat;

import lombok.Value;

@Value
public class SeatDto {
    private Integer seatRow;
    private Integer seatCol;
}
