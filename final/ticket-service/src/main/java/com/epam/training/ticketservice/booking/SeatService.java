package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningDto;
import com.epam.training.ticketservice.screening.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private final ScreeningService screeningService;

    public List<SeatDto> getSeatsForScreening(ScreeningDto screeningDto) {
        Screening screening = screeningService.getScreeningByMovieAndRoomAndStartTime(screeningDto.getMovieTitle(),
                screeningDto.getRoomName(),
                screeningDto.getStartTime()).orElseThrow(() -> new IllegalArgumentException("Screening does not exists"));
        return seatRepository.findSeatsByScreening(screening)
                .stream()
                .map(this::convertSeatToDto).collect(Collectors.toList());
    }

    private SeatDto convertSeatToDto(Seat seat) {
        return new SeatDto(seat.getSeatRow(), seat.getSeatCol());
    }
}
