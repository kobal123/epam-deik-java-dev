package com.epam.training.ticketservice.movie;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    @Override
    public void updateMovie(MovieDto movie) {
        Movie movieToUpdate = movieRepository.findByName(movie.getName())
                .orElseThrow(() -> new IllegalArgumentException("Movie does not exists")
                );
        updateMovieWithMovieDto(movieToUpdate, movie);
        movieRepository.save(movieToUpdate);
    }

    @Override
    public void deleteMovieByName(String name) {
        Movie movie = movieRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException(
                String.format("Deleting movie %s failed, movie does not exists", name)));

        movieRepository.deleteById(movie.getId());
    }

    @Override
    public void createMovie(MovieDto movie) {
        Movie movieToSave = movieFromDto(movie);
        movieRepository.save(movieToSave);
    }

    @Override
    public Optional<MovieDto> getMovieByName(String name) {
        Movie movie = movieRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Could not find movie!"));
        MovieDto dto = convertToDto(movie);
        return Optional.of(dto);
    }

    @Override
    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private void updateMovieWithMovieDto(Movie movie,MovieDto dto) {
        movie.setName(dto.getName());
        movie.setGenre(dto.getGenre());
        movie.setScreenTime(dto.getScreenTime());
    }

    private Movie movieFromDto(MovieDto dto) {
        return new Movie(dto.getName(), dto.getGenre(), dto.getScreenTime());
    }
    private MovieDto convertToDto(Movie movie) {
        return new MovieDto(movie.getName(), movie.getGenre(), movie.getScreenTime());
    }
}
