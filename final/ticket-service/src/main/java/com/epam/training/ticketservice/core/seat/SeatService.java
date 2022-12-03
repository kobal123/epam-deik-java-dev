package com.epam.training.ticketservice.core.seat;

import com.epam.training.ticketservice.core.screening.ScreeningDto;

import java.util.List;

public interface SeatService {

    public List<SeatDto> getSeatsForScreening(ScreeningDto screeningDto);

}
