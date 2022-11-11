package com.epam.training.ticketservice.screening;

import javax.persistence.Column;
import java.time.LocalDateTime;

public class ScreeningDTO {
    private String movieTitle;
    private String roomName;
    private LocalDateTime startTime;
}
