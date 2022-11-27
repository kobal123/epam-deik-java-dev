package com.epam.training.ticketservice.screening;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScreeningConverter {
    public ScreeningDto toDto(Screening screening) {
        return new ScreeningDto(screening.getMovieTitle(),
                screening.getRoomName(),
                screening.getStartTime()
        );
    }
}
