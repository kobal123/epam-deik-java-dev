package com.epam.training.ticketservice.movie;

import com.epam.training.ticketservice.movie.exception.MovieNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl underTest;

    @Test
    void testGetAllMovies() {
        // given
        List<Movie> expected = List.of(
                new Movie("movie1", "drama", 120),
                new Movie("movie2", "action", 134),
                new Movie("movie3", "fantasy", 115)
        );
        Mockito.when(movieRepository.findAll()).thenReturn(expected);

        // when
        List<Movie> actual = underTest.getAllMovies();

        // then
        assertEquals(expected, actual);
    }

    @Test
    void testUpdateMovieByIdShouldThrowMovieNotFoundExceptionWhenMovieDoesNotExists() {
        // given
        String movieName = "name";
        Movie movie = new Movie(movieName,"drama",123);
        Mockito.when(movieRepository.findById(movieName)).thenReturn(Optional.empty());

        // when
        // then

        assertThrows(MovieNotFoundException.class,
                () -> underTest.updateMovie(movie));
    }

    @Test
    void testDeleteMovieByIdShouldThrowMovieNotFoundExceptionWhenMovieDoesNotExists() {
        // given
        String movieName = "name";
        Movie movie = new Movie(movieName,"drama",123);
        Mockito.when(movieRepository.findById(movieName)).thenReturn(Optional.empty());

        // when
        // then

        assertThrows(MovieNotFoundException.class,
                () -> underTest.deleteMovieByName(movieName));
    }

}