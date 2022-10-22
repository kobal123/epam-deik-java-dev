package com.epam.training.ticketservice.movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    void updateMovie(Movie movie);

    void deleteMovieByName(String name);

    void createMovie(Movie movie);

    Optional<Movie> getMovieByName(String name);

    List<Movie> getAllMovies();
}
