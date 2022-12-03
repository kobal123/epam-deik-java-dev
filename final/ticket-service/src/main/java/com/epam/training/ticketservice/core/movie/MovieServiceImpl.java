package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.pricecomponent.PriceComponent;
import com.epam.training.ticketservice.core.pricecomponent.PriceComponentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final PriceComponentRepository priceComponentRepository;


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
        Optional<Movie> movie = movieRepository.findByName(name);
        movie.ifPresent(value -> movieRepository.deleteById(value.getId()));
    }

    @Override
    public void createMovie(MovieDto movie) {

        Movie movieToSave = movieFromDto(movie);
        movieRepository.save(movieToSave);
    }

    @Override
    public Optional<MovieDto> getMovieByName(String name) {
        Optional<Movie> movie = movieRepository.findByName(name);
        return movie.map(this::convertToDto);
    }

    @Override
    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void attachPriceComponent(String componentName, String movieName) {
        Movie movie = movieRepository.findByName(movieName)
                .orElseThrow(() -> new IllegalArgumentException("No such movie"));

        PriceComponent component = priceComponentRepository.findById(componentName)
                .orElseThrow(() -> new IllegalArgumentException("No such price component"));

        movie.addPriceComponent(component);
        movieRepository.save(movie);
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
