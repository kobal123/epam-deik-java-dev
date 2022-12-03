package com.epam.training.ticketservice.seat;

import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningDto;
import com.epam.training.ticketservice.screening.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService{
    private final SeatRepository seatRepository;

    private final ScreeningRepository screeningRepository;

    public List<SeatDto> getSeatsForScreening(ScreeningDto screeningDto) {
        Screening screening = screeningRepository.findScreeningByMovieAndRoomAndStartTime(screeningDto.getMovieTitle(),
                screeningDto.getRoomName(),
                screeningDto.getStartTime())
                .orElseThrow(() -> new IllegalArgumentException("Screening does not exists"));

        return seatRepository.findSeatsByScreening(screening)
                .stream()
                .map(this::convertSeatToDto).collect(Collectors.toList());
    }

    private SeatDto convertSeatToDto(Seat seat) {
        return new SeatDto(seat.getSeatRow(), seat.getSeatCol());
    }
}
