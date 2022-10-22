package com.epam.training.ticketservice.movie;

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
        movieRepository.save(movie);
    }

    @Override
    public void deleteMovieByName(String name) {
        Optional<Movie> movie = movieRepository.findById(name);
        if (movie.isEmpty()) {
            throw new RuntimeException("Movie does not exist" + movie);
        }
        movieRepository.deleteById(name);
    }

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
