package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.bookingprice.BookingPrice;
import com.epam.training.ticketservice.bookingprice.BookingPriceRepository;
import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.movie.MovieRepository;
import com.epam.training.ticketservice.movie.MovieService;
import com.epam.training.ticketservice.pricecomponent.PriceComponent;
import com.epam.training.ticketservice.pricecomponent.PriceComponentRepository;
import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.room.RoomRepository;
import com.epam.training.ticketservice.screening.exception.OverlappingScreeningBreakTimeException;
import com.epam.training.ticketservice.screening.exception.OverlappingScreeningException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScreeningServiceImplTest {

    @Mock
    private ScreeningRepository screeningRepository;// = Mockito.mock(ScreeningRepository.class);
    @Mock
    private MovieService movieService;// = Mockito.mock(MovieRepository.class);
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private PriceComponentRepository priceComponentRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private BookingPriceRepository bookingPriceRepository;
    @InjectMocks
    private  ScreeningServiceImpl underTest;// = new ScreeningServiceImpl(formatter, screeningRepository,movieRepository );

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final BookingPrice bookingPrice = new BookingPrice();

    @Test
    void testCreateScreeningShouldThrowOverlappingScreeningExceptionWhenScreeningStartsInInSameRoom() {
        // Given
        LocalDateTime existingScreeningStartTime = LocalDateTime.parse("2002-04-11 16:10", formatter);
        LocalDateTime screeningToSaveStartTime = LocalDateTime.parse("2002-04-11 16:15", formatter);
        Movie movieOne = new Movie("Movie", "drama", 135);
        Movie movieTwo = new Movie("Movie2", "action", 200);
        Room roomOne = new Room("Room", 10, 10);
        Screening existingScreening = new Screening(movieOne,
                roomOne,
                existingScreeningStartTime);

        ScreeningDto screeningToSave = new ScreeningDto("Movie2",
                "Room",
                screeningToSaveStartTime);

        List<Screening> screenings = List.of(existingScreening);
        when(screeningRepository.findAll()).thenReturn(screenings);
//        when(movieRepository.findByName(movieOne.getName())).thenReturn(Optional.of(movieOne));
        when(movieRepository.findByName(screeningToSave.getMovieTitle())).thenReturn(Optional.of(movieTwo));
        //when(roomRepository.findByName(roomOne.getName())).thenReturn(Optional.of(roomOne));

        // When
        // Then
        assertThrows(OverlappingScreeningException.class,
                () -> underTest.createScreening(screeningToSave));
    }


    @Test
    void testCreateScreeningShouldThrowOverlappingScreeningBreakTimeExceptionWhenScreeningStartsInBreakTimeInSameRoom() {
        // Given
        LocalDateTime existingScreeningStartTime = LocalDateTime.parse("2002-04-11 14:00", formatter);
        LocalDateTime screeningToSaveStartTime = LocalDateTime.parse("2002-04-11 16:05", formatter);
        Movie movieOne = new Movie("Movie", "drama", 120);
        Movie movieTwo = new Movie("Movie2", "action", 200);
        Room roomOne = new Room("Room", 10, 10);
        Room roomTwo = new Room("Room2", 15, 10);

        Screening existingScreening = new Screening(movieOne,
                roomOne,
                existingScreeningStartTime);

        ScreeningDto screeningToSave = new ScreeningDto("Movie2",
                "Room",
                screeningToSaveStartTime);

        List<Screening> screenings = List.of(existingScreening);
        when(screeningRepository.findAll()).thenReturn(screenings);
//        when(movieRepository.findByName("Movie")).thenReturn(Optional.of(movieOne));
        when(movieRepository.findByName("Movie2")).thenReturn(Optional.of(movieTwo));

        // When
        // Then
        assertThrows(OverlappingScreeningBreakTimeException.class,
                () -> underTest.createScreening(screeningToSave));
        //verify(movieRepository).findByName("Movie");
        verify(movieRepository).findByName("Movie2");
    }

    @Test
    void testCreateScreeningShouldPassWhenScreeningStartsInBreakTimeInDifferentRoom() {
        // Given
        LocalDateTime existingScreeningStartTime = LocalDateTime.parse("2002-04-11 14:00", formatter);
        LocalDateTime screeningToSaveStartTime = LocalDateTime.parse("2002-04-11 16:05", formatter);
        Movie movieOne = new Movie("Movie", "drama", 120);
        Movie movieTwo = new Movie("Movie2", "action", 200);
        Room roomOne = new Room("Room", 10, 10);
        Room roomTwo = new Room("Room2", 10, 10);

                Screening existingScreening = new Screening(movieOne,
                        roomOne,
                        existingScreeningStartTime);

        ScreeningDto screeningToSave = new ScreeningDto("Movie2",
                "Room2",
                screeningToSaveStartTime);

        List<Screening> screenings = List.of(existingScreening);
        when(screeningRepository.findAll()).thenReturn(screenings);
        when(movieRepository.findByName("Movie2")).thenReturn(Optional.of(movieTwo));
        when(roomRepository.findByName(roomTwo.getName())).thenReturn(Optional.of(roomTwo));
        when(bookingPriceRepository.findById(1L)).thenReturn(Optional.of(bookingPrice));

        // When
        // Then
        assertDoesNotThrow(() -> underTest.createScreening(screeningToSave));
    }



    @Test
    void testDeleteScreeningShouldCallRepositoryIfScreeningExists() {
        LocalDateTime time = LocalDateTime.parse("2002-04-11 16:05", formatter);
        Movie movie = new Movie("Movie", "drama", 120);
        Room room = new Room("Room", 10, 10);
        Screening screening = new Screening(movie, room, time);

        ScreeningDto screeningToDelete = new ScreeningDto("Movie",
                "Room",
                time);

        when(screeningRepository.
                findScreeningByMovieAndRoomAndStartTime(
                        screeningToDelete.getMovieTitle(),
                        screeningToDelete.getRoomName(),
                        screeningToDelete.getStartTime()))
                .thenReturn(Optional.of(screening));

        // When
        underTest.deleteScreening(screeningToDelete);
        // Then
        verify(screeningRepository).deleteById(screening.getId());
        verify(screeningRepository).findScreeningByMovieAndRoomAndStartTime(
                screeningToDelete.getMovieTitle(),
                screeningToDelete.getRoomName(),
                screeningToDelete.getStartTime());
    }

    @Test
    void testAttachPriceComponentShouldThrowIllegalArgumentExceptionWhenPriceComponentDoesNotExist() {
        // given
        LocalDateTime time = LocalDateTime.parse("2002-04-11 16:05", formatter);
        ScreeningDto screening = new ScreeningDto("movie", "room", time);
        String priceComponentName = "priceComponent";
        Mockito.when(priceComponentRepository.findById(priceComponentName))
                .thenReturn(Optional.empty());

        //when
        //then
        assertThrows(IllegalArgumentException.class,
                () -> underTest.attachPriceComponent(priceComponentName, screening));
    }

    @Test
    void testAttachPriceComponentShouldCallRepositoryWhenInputIsValid() {
        // given
        LocalDateTime time = LocalDateTime.parse("2002-04-11 16:05", formatter);
        Movie movie = new Movie("Movie", "drama", 120);
        Room room = new Room("Room", 10, 10);
        Screening screening = new Screening(movie, room, time);
        ScreeningDto screeningDto = new ScreeningDto("Movie", "Room", time);

        PriceComponent priceComponent = new PriceComponent("component", 1500);
        Mockito.when(priceComponentRepository.findById(priceComponent.getName()))
                .thenReturn(Optional.of(priceComponent));

        when(screeningRepository.
                findScreeningByMovieAndRoomAndStartTime(
                        screeningDto.getMovieTitle(),
                        screeningDto.getRoomName(),
                        screeningDto.getStartTime()))
                .thenReturn(Optional.of(screening));
        //when
        underTest.attachPriceComponent(priceComponent.getName(), screeningDto);
        //then
        Mockito.verify(screeningRepository)
                .save(screening);
        assertTrue(screening.getPriceComponents().contains(priceComponent));
    }


}