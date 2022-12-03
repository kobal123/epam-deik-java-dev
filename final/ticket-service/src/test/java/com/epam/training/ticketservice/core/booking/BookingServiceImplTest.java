package com.epam.training.ticketservice.core.booking;

import com.epam.training.ticketservice.core.booking.Booking;
import com.epam.training.ticketservice.core.booking.BookingDto;
import com.epam.training.ticketservice.core.booking.BookingRepository;
import com.epam.training.ticketservice.core.booking.BookingServiceImpl;
import com.epam.training.ticketservice.core.bookingprice.BookingPrice;
import com.epam.training.ticketservice.core.bookingprice.BookingPriceService;
import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.room.Room;
import com.epam.training.ticketservice.core.screening.Screening;
import com.epam.training.ticketservice.core.screening.ScreeningConverter;
import com.epam.training.ticketservice.core.screening.ScreeningDto;
import com.epam.training.ticketservice.core.screening.ScreeningRepository;
import com.epam.training.ticketservice.core.seat.Seat;
import com.epam.training.ticketservice.core.seat.SeatDto;
import com.epam.training.ticketservice.core.user.model.User;
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
    private BookingPriceService bookingPriceService;

    @Mock
    private ScreeningConverter screeningConverter;

    @Mock
    private BookingRepository bookingRepository;


    @InjectMocks
    private BookingServiceImpl underTest;

    private static final LocalDateTime TIME = LocalDateTime.now();
    private static final ScreeningDto screeningDto = new ScreeningDto("movie", "room", TIME);
    private static final Movie MOVIE = new Movie("movie", "drama", 135);
    private static final Room ROOM = new Room("room", 10, 10);
    private static final Screening SCREENING = new Screening(MOVIE, ROOM, TIME);

    private static final Set<Seat> SEATS = Set.of(new Seat(1,2));
    private static final Set<SeatDto> SEAT_DTOS = Set.of(new SeatDto(1,2));


    @Test
    void testCreateBookingShouldThrowIllegalArgumentExceptionWhenScreeningDoesNotExists() {
        // Given
        Mockito.when(screeningRepository.findScreeningByMovieAndRoomAndStartTime("movie", "room", TIME)).thenReturn(Optional.empty());

        // when
        // then
        assertThrows(IllegalArgumentException.class,
                () -> underTest.createBooking(screeningDto,SEAT_DTOS));
        Mockito.verify(screeningRepository).findScreeningByMovieAndRoomAndStartTime("movie", "room", TIME);
    }


    @Test
    void testGetAllBookings() {
        // given
        List<Booking> bookings = List.of(
                new Booking(new User(), SCREENING,SEATS)
        );

        List<BookingDto> expected = List.of(
                new BookingDto(screeningDto, SEAT_DTOS,null)
        );
        Mockito.when(screeningConverter.toDto(SCREENING)).thenReturn(screeningDto);
        Mockito.when(bookingRepository.findAll()).thenReturn(bookings);

        // when
        List<BookingDto> actual = underTest.getAllBookings();

        // then
        assertEquals(expected, actual);


    }

    @Test
    void testPreCheckBookingPriceShouldThrowIllegalArgumentExceptionIfScreeningDoesNotExist() {
        // given
        Mockito.when(screeningRepository
                .findScreeningByMovieAndRoomAndStartTime(MOVIE.getName(), ROOM.getName(), TIME))
                .thenReturn(Optional.empty());

        // when
        // then
        assertThrows(IllegalArgumentException.class,
                () -> underTest.preCheckBookingPrice(screeningDto, SEAT_DTOS));

    }


    @Test
    void testPreCheckBookingPriceShouldPassIfScreeningExists() {
        // given
        Screening screening = new Screening(MOVIE, ROOM, TIME);
        screening.setBookingPrice(new BookingPrice());
        Long expectedPrice = 1500L;

        Mockito.when(screeningRepository
                        .findScreeningByMovieAndRoomAndStartTime(MOVIE.getName(), ROOM.getName(), TIME))
                .thenReturn(Optional.of(screening));
        Mockito.when(bookingPriceService.getBookingPrice(screening, SEATS)).thenReturn(1500L);

        // when
        Long actualPrice = underTest.preCheckBookingPrice(screeningDto, SEAT_DTOS);
        // then
        assertEquals(expectedPrice, actualPrice);
    }
}