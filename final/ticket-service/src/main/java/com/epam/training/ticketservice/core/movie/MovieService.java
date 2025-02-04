package com.epam.training.ticketservice.core.movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    void updateMovie(MovieDto movie);

    void deleteMovieByName(String name);

    void createMovie(MovieDto movie);

    Optional<MovieDto> getMovieByName(String name);

    List<MovieDto> getAllMovies();

    void attachPriceComponent(String componentName, String movie);

}
