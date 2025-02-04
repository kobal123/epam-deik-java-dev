package com.epam.training.ticketservice.core.room;

import lombok.Value;

@Value
public class RoomDto {
    String name;
    Integer seatRows;
    Integer seatCols;

    public Integer getCapacity() {
        return seatCols * seatRows;
    }
}
