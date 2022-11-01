package com.epam.training.ticketservice.movie;

import com.epam.training.ticketservice.movie.exception.MovieNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    @Override
    public void updateMovie(Movie movie) {
        Optional<Movie> optionalMovie = movieRepository.findById(movie.getName());
        if (optionalMovie.isEmpty()) {
            throw new MovieNotFoundException(
                    String.format("Updating movie %s failed, movie does not exists",movie.getName())
            );
        }
        movieRepository.save(movie);
    }

    @Override
    public void deleteMovieByName(String name) {
        Optional<Movie> optionalMovie = movieRepository.findById(name);
        if (optionalMovie.isEmpty()) {
            throw new MovieNotFoundException(
                    String.format("Deleting movie %s failed, movie does not exists",name)
            );
        }
        movieRepository.save(optionalMovie.get());    }

    @Override
    public void createMovie(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public Optional<Movie> getMovieByName(String name) {
        return movieRepository.findById(name);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

}
