package com.epam.training.ticketservice.core.screening;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScreeningConverter {
    public ScreeningDto toDto(Screening screening) {
        return new ScreeningDto(screening.getMovie().getName(),
                screening.getRoom().getName(),
                screening.getStartTime()
        );
    }
}
