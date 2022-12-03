package com.epam.training.ticketservice.core.seat;

import com.epam.training.ticketservice.core.screening.Screening;
import com.epam.training.ticketservice.core.screening.ScreeningDto;
import com.epam.training.ticketservice.core.screening.ScreeningRepository;
import com.epam.training.ticketservice.core.seat.Seat;
import com.epam.training.ticketservice.core.seat.SeatDto;
import com.epam.training.ticketservice.core.seat.SeatRepository;
import com.epam.training.ticketservice.core.seat.SeatServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class SeatServiceImplTest {

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private ScreeningRepository screeningRepository;

    @InjectMocks
    private SeatServiceImpl underTest;

    @Test
    void getSeatsForScreeningShouldThrowIllegalArgumentExceptionWhenScreeningDoesNotExist() {
        // given
        LocalDateTime time = LocalDateTime.now();
        ScreeningDto screeningDto = new ScreeningDto("Movie",
                "Room",
                time);
        when(screeningRepository.
                findScreeningByMovieAndRoomAndStartTime(
                        screeningDto.getMovieTitle(),
                        screeningDto.getRoomName(),
                        screeningDto.getStartTime()))
                .thenReturn(Optional.empty());

        // when
        // then
        assertThrows(IllegalArgumentException.class,
                () -> underTest.getSeatsForScreening(screeningDto));
    }

    @Test
    void getSeatsForScreeningShouldCallRepositoryWhenInputIsValid() {
        // given

        List<SeatDto> expected = List.of(
                new SeatDto(10,10),
                new SeatDto(13,1),
                new SeatDto(10,20),
                new SeatDto(10,11)
        );

        List<Seat> seats = List.of(
                new Seat(10,10),
                new Seat(13,1),
                new Seat(10,20),
                new Seat(10,11)
        );
        LocalDateTime time = LocalDateTime.now();
        ScreeningDto screeningDto = new ScreeningDto("Movie",
                "Room",
                time);
        when(screeningRepository.
                findScreeningByMovieAndRoomAndStartTime(
                        screeningDto.getMovieTitle(),
                        screeningDto.getRoomName(),
                        screeningDto.getStartTime()))
                .thenReturn(Optional.of(new Screening()));

        when(seatRepository.findSeatsByScreening(new Screening())).thenReturn(seats);
        // when
        List<SeatDto> actual = underTest.getSeatsForScreening(screeningDto);
        // then
        assertEquals(expected, actual);
        verify(screeningRepository).findScreeningByMovieAndRoomAndStartTime(
                screeningDto.getMovieTitle(),
                screeningDto.getRoomName(),
                screeningDto.getStartTime()
        );


    }
}