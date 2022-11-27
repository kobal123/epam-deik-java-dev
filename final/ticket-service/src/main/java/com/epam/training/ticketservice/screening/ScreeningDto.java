package com.epam.training.ticketservice.screening;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ScreeningDto {
    private final String movieTitle;
    private final String roomName;
    private final LocalDateTime startTime;
}
