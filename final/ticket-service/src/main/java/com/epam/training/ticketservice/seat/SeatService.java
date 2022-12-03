package com.epam.training.ticketservice.seat;

import com.epam.training.ticketservice.screening.ScreeningDto;

import java.util.List;

public interface SeatService {

    public List<SeatDto> getSeatsForScreening(ScreeningDto screeningDto);

}
