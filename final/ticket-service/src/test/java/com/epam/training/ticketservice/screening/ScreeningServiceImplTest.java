package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.movie.MovieDto;
import com.epam.training.ticketservice.movie.MovieRepository;
import com.epam.training.ticketservice.movie.MovieService;
import com.epam.training.ticketservice.movie.MovieServiceImpl;
import com.epam.training.ticketservice.room.Room;
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
import java.util.ArrayList;
import java.util.Collections;
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
    @InjectMocks
    private  ScreeningServiceImpl underTest;// = new ScreeningServiceImpl(formatter, screeningRepository,movieRepository );

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Test
    void testValidationShouldThrowOverlappingScreeningExceptionWhenScreeningStartsInInSameRoom() {
        // Given
        LocalDateTime existingScreeningStartTime = LocalDateTime.parse("2002-04-11 16:10", formatter);
        LocalDateTime screeningToSaveStartTime = LocalDateTime.parse("2002-04-11 16:15", formatter);

        Screening existingScreening = new Screening("Movie",
                "Room",
                existingScreeningStartTime);

        ScreeningDto screeningToSave = new ScreeningDto("Movie2",
                "Room",
                screeningToSaveStartTime);
        Movie movieOne = new Movie("Movie", "drama", 135);
        Movie movieTwo = new Movie("Movie2", "action", 200);
        List<Screening> screenings = List.of(existingScreening);
        when(screeningRepository.findAll()).thenReturn(screenings);
        when(movieRepository.findByName(existingScreening.getMovieTitle())).thenReturn(Optional.of(movieOne));
        when(movieRepository.findByName(screeningToSave.getMovieTitle())).thenReturn(Optional.of(movieTwo));

        // When
        // Then
        assertThrows(OverlappingScreeningException.class,
                () -> underTest.createScreening(screeningToSave));
    }


    @Test
    void testValidationShouldThrowOverlappingScreeningBreakTimeExceptionWhenScreeningStartsInBreakTimeInSameRoom() {
        // Given
        LocalDateTime existingScreeningStartTime = LocalDateTime.parse("2002-04-11 14:00", formatter);
        LocalDateTime screeningToSaveStartTime = LocalDateTime.parse("2002-04-11 16:05", formatter);

        Screening existingScreening = new Screening("Movie",
                "Room",
                existingScreeningStartTime);

        ScreeningDto screeningToSave = new ScreeningDto("Movie2",
                "Room",
                screeningToSaveStartTime);
        Movie movieOne = new Movie("Movie", "drama", 120);
        Movie movieTwo = new Movie("Movie2", "action", 200);
        List<Screening> screenings = List.of(existingScreening);
        when(screeningRepository.findAll()).thenReturn(screenings);
        when(movieRepository.findByName("Movie")).thenReturn(Optional.of(movieOne));
        when(movieRepository.findByName("Movie2")).thenReturn(Optional.of(movieTwo));

        // When
        // Then
        assertThrows(OverlappingScreeningBreakTimeException.class,
                () -> underTest.createScreening(screeningToSave));
        verify(movieRepository).findByName("Movie");
        verify(movieRepository).findByName("Movie2");
    }

    @Test
    void testDateValidationShouldPassWhenScreeningStartsInBreakTimeInDifferentRoom() {
        // Given
        LocalDateTime existingScreeningStartTime = LocalDateTime.parse("2002-04-11 14:00", formatter);
        LocalDateTime screeningToSaveStartTime = LocalDateTime.parse("2002-04-11 16:05", formatter);

        Screening existingScreening = new Screening("Movie",
                "Room",
                existingScreeningStartTime);

        ScreeningDto screeningToSave = new ScreeningDto("Movie2",
                "Room2",
                screeningToSaveStartTime);
        Movie movieOne = new Movie("Movie", "drama", 120);
        Movie movieTwo = new Movie("Movie2", "action", 200);
        List<Screening> screenings = List.of(existingScreening);
        when(screeningRepository.findAll()).thenReturn(screenings);

        // When
        // Then
        assertDoesNotThrow(() -> underTest.createScreening(screeningToSave));
    }

    @Test
    void testDeleteScreeningShouldThrowIllegalArgumentExceptionWhenScreeningDoesNotExists() {
        LocalDateTime screeningToSaveStartTime = LocalDateTime.parse("2002-04-11 16:05", formatter);

        ScreeningDto screeningToDelete = new ScreeningDto("Movie2",
                "Room",
                screeningToSaveStartTime);

        when(screeningRepository.
                findScreeningByMovieAndRoomAndStartTime(
                        screeningToDelete.getMovieTitle(),
                        screeningToDelete.getRoomName(),
                        screeningToDelete.getStartTime()))
                .thenReturn(Optional.empty());


        // When
        // Then
        assertThrows(IllegalArgumentException.class,
                () -> underTest.deleteScreening(screeningToDelete));

        verify(screeningRepository).findScreeningByMovieAndRoomAndStartTime(
                screeningToDelete.getMovieTitle(),
                screeningToDelete.getRoomName(),
                screeningToDelete.getStartTime());
    }

}