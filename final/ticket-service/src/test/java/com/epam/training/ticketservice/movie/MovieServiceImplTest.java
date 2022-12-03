package com.epam.training.ticketservice.movie;

import com.epam.training.ticketservice.pricecomponent.PriceComponent;
import com.epam.training.ticketservice.pricecomponent.PriceComponentRepository;
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

    @Mock
    private PriceComponentRepository priceComponentRepository;

    @InjectMocks
    private MovieServiceImpl underTest;

    @Test
    void testGetAllMovies() {
        // given
        List<Movie> movies = List.of(
                new Movie("movie1", "drama", 120),
                new Movie("movie2", "action", 134),
                new Movie("movie3", "fantasy", 115)
        );
        List<MovieDto> expected  = List.of(
                new MovieDto("movie1", "drama", 120),
                new MovieDto("movie2", "action", 134),
                new MovieDto("movie3", "fantasy", 115)
        );
        Mockito.when(movieRepository.findAll()).thenReturn(movies);

        // when
        List<MovieDto> actual = underTest.getAllMovies();

        // then
        assertEquals(expected, actual);
    }

    @Test
    void testUpdateMovieByIdShouldThrowMovieNotFoundExceptionWhenMovieDoesNotExists() {
        // given
        String movieName = "name";
        MovieDto movie = new MovieDto(movieName,"drama",123);
        Mockito.when(movieRepository.findByName(movieName)).thenReturn(Optional.empty());

        // when
        // then
        assertThrows(IllegalArgumentException.class,
                () -> underTest.updateMovie(movie));
    }


    @Test
    void testUpdateMovieShouldCallRepositoryWhenInputIsValid() {
        // given
        String movieName = "name";
        MovieDto movieDto = new MovieDto(movieName,"action",123);
        Movie movie = new Movie(movieName,"drama",123);
        Movie movieAfterUpdate = new Movie(movieName,"action",123);

        Mockito.when(movieRepository.findByName(movieName)).thenReturn(Optional.of(movie));

        // when
        underTest.updateMovie(movieDto);
        // then
        Mockito.verify(movieRepository).findByName(movieName);
        Mockito.verify(movieRepository).save(movieAfterUpdate);

    }


    @Test
    void testDeleteMovieByNameShouldCallRepository() {
        // given
        Movie movie = new Movie("name","genre", 135);
        Mockito.when(movieRepository.findByName(movie.getName())).thenReturn(Optional.of(movie));
        // when
        underTest.deleteMovieByName(movie.getName());

        // then
        Mockito.verify(movieRepository).findByName(movie.getName());
        Mockito.verify(movieRepository).deleteById(movie.getId());
    }


    @Test
    void testGetMovieByNameShouldReturnOptionalEmptyWhenMovieDoesNotExist() {
        // given
        String movieName = "movie";
        Mockito.when(movieRepository.findByName(movieName)).thenReturn(Optional.empty());

        // when
        Optional<MovieDto> result = underTest.getMovieByName(movieName);

        // then
        assertTrue(result.isEmpty());
        Mockito.verify(movieRepository).findByName(movieName);
    }


    @Test
    void testGetMovieByNameShouldReturnCorrectValueWhenInputIsValid() {
        // given

        Movie movie = new Movie("name", "genre", 135);
        MovieDto expected = new MovieDto("name", "genre", 135);

        Mockito.when(movieRepository.findByName(movie.getName())).thenReturn(Optional.of(movie));

        // when
        Optional<MovieDto> actual = underTest.getMovieByName(movie.getName());

        // then
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
        Mockito.verify(movieRepository).findByName(movie.getName());
    }

    @Test
    void testAttachPriceComponentShouldCallRepositoryWhenInputIsValid() {
        // given
        Movie movie = new Movie("Movie", "drama", 120);

        PriceComponent priceComponent = new PriceComponent("component", 1500);
        Mockito.when(priceComponentRepository.findById(priceComponent.getName()))
                .thenReturn(Optional.of(priceComponent));
        Mockito.when(movieRepository.findByName(movie.getName()))
                .thenReturn(Optional.of(movie));


        //when
        underTest.attachPriceComponent(priceComponent.getName(), movie.getName());
        //then
        Mockito.verify(movieRepository).findByName(movie.getName());
        Mockito.verify(movieRepository)
                .save(movie);
        assertTrue(movie.getPriceComponents().contains(priceComponent));
    }
}