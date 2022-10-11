package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.movie.MovieService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class MovieCommand {
    private final MovieService movieService;

    public MovieCommand(MovieService movieService) {
        this.movieService = movieService;
    }

    @ShellMethod(value = "Create a new movie", key = "create movie")
    void createMovie(String title, String genre, int screenTime) {
        Movie movie = new Movie(title, genre, screenTime);
        movieService.createMovie(movie);
    }

    @ShellMethod(value = "Update a movie", key = "update movie")
    void updateMovie(String title, String genre, int screenTime) {
        Movie movie = new Movie(title, genre, screenTime);
        movieService.updateMovie(movie);

    }

    @ShellMethod(value = "Delete a movie by its name", key = "delete movie")
    void deleteMovie(String title) {
        movieService.deleteMovieByName(title);

    }


    @ShellMethod(value = "List all movies", key = "list movies")
    void listMovies() {
        List<Movie> movies = movieService.getAllMovies();
        if(movies.isEmpty()){
            System.out.println("There are no movies at the moment");
        }
        StringBuilder stringBuilder = new StringBuilder();
        //for(Movie movie : movies){
         //   stringBuilder.append(String.format("%s (%s, %s)",movie.getName(),movie.getGenre(),movie.getScreenTimeInMinutes()));
        //}
        movies.stream()
                .map(movie -> String.format("%s (%s, %s minutes)",movie.getName(),movie.getGenre(),movie.getScreenTimeInMinutes()))
                .forEach(System.out::println);
    }

}
