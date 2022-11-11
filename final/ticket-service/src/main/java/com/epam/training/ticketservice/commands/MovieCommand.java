package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.movie.MovieService;
import com.epam.training.ticketservice.user.UserDTO;
import com.epam.training.ticketservice.user.UserService;
import com.epam.training.ticketservice.user.model.Role;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.Optional;

@ShellComponent
public class MovieCommand {
    private final MovieService movieService;
    private final UserService userService;

    public MovieCommand(MovieService movieService, UserService userService) {
        this.movieService = movieService;
        this.userService = userService;
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

        if (movies.isEmpty()) {
            System.out.println("There are no movies at the moment");
        }

        StringBuilder stringBuilder = new StringBuilder();
        movies.stream()
                .map(movie -> String.format("%s (%s, %s minutes)",
                        movie.getName(),
                        movie.getGenre(),
                        movie.getScreenTime()))
                .forEach(System.out::println);
    }

    @ShellMethodAvailability({"deleteMovie","updateMovie","createMovie"})
    public Availability isAdmin() {
        Optional<UserDTO> userDTO = userService.describe();
        return userDTO.isPresent() && userDTO.get().hasRole(Role.ADMIN)
                ? Availability.available()
                : Availability.unavailable("User is not an admin");
    }
}
