package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningConverter;
import com.epam.training.ticketservice.screening.ScreeningDto;
import com.epam.training.ticketservice.screening.ScreeningRepository;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.user.UserDto;
import com.epam.training.ticketservice.user.UserService;
import com.epam.training.ticketservice.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private ScreeningRepository screeningRepository;
    @Mock
    private ScreeningService screeningService;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private ScreeningConverter screeningConverter;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private BookingServiceImpl underTest;

    private static final LocalDateTime TIME = LocalDateTime.now();
    private static final ScreeningDto screeningDto = new ScreeningDto("movie", "room", TIME);
    private static final Screening screening = new Screening("movie", "room", TIME);

    private static final Set<Seat> SEATS = Set.of(new Seat(1,2));
    private static final Set<SeatDto> SEAT_DTOS = Set.of(new SeatDto(1,2));


    @Test
    void testCreateBookingShouldThrowIllegalArgumentExceptionWhenScreeningDoesNotExists() {
        // Given
        Mockito.when(screeningService.getScreeningByMovieAndRoomAndStartTime("movie", "room", TIME)).thenReturn(Optional.empty());

        // when
        // then
        assertThrows(IllegalArgumentException.class,
                () -> underTest.createBooking(screeningDto,SEAT_DTOS));
        Mockito.verify(screeningService).getScreeningByMovieAndRoomAndStartTime("movie", "room", TIME);
    }


    @Test
    void testGetAllBookings() {
        // given

        List<Booking> bookings = List.of(
                new Booking(new User(),screening,SEATS)
        );

        List<BookingDto> expected = List.of(
                new BookingDto(screeningDto, SEAT_DTOS,null)
        );
        Mockito.when(screeningConverter.toDto(screening)).thenReturn(screeningDto);
        Mockito.when(bookingRepository.findAll()).thenReturn(bookings);

        // when
        List<BookingDto> actual = underTest.getAllBookings();

        // then
        assertEquals(expected, actual);


    }
}