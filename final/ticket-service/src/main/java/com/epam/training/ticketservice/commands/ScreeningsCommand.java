package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.movie.MovieDto;
import com.epam.training.ticketservice.movie.MovieService;
import com.epam.training.ticketservice.screening.ScreeningDto;
import com.epam.training.ticketservice.screening.exception.OverlappingScreeningBreakTimeException;
import com.epam.training.ticketservice.screening.exception.OverlappingScreeningException;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.user.UserDto;
import com.epam.training.ticketservice.user.UserService;
import com.epam.training.ticketservice.user.model.Role;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import java.text.Collator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@ShellComponent
public class ScreeningsCommand {

    private final ScreeningService screeningService;
    private final MovieService movieService;
    private final DateTimeFormatter dateTimeFormatter;
    private final UserService userService;

    public ScreeningsCommand(ScreeningService screeningService,
                             MovieService movieService,
                             DateTimeFormatter dateTimeFormatter, UserService userService) {
        this.screeningService = screeningService;
        this.movieService = movieService;
        this.dateTimeFormatter = dateTimeFormatter;
        this.userService = userService;
    }

    @ShellMethod(value = "List all screenings", key = "list screenings")
    void listScreenings() {
        List<ScreeningDto> screenings = screeningService.getAllScreenings();
        screenings.sort(Comparator.comparing(ScreeningDto::getMovieTitle, Collator.getInstance()));
        if (screenings.isEmpty()) {
            System.out.println("There are no screenings");
        }

        String format = "%s (%s, %d minutes), screened in room %s, at %s";
        for (ScreeningDto screening : screenings) {
            MovieDto movie = movieService.getMovieByName(screening.getMovieTitle())
                    .orElseThrow(() -> new RuntimeException("movie does not exists"));

            String message = String.format(format,
                    movie.getName(),
                    movie.getGenre(),
                    movie.getScreenTime(),
                    screening.getRoomName(),
                    dateTimeFormatter.format(screening.getStartTime()));

            System.out.println(message);
        }
    }


    @ShellMethodAvailability("isAdmin")
    @ShellMethod(value = "Create a new screening", key = "create screening")
    void createScreening(String movieTitle, String roomName, String startTime) {
        try {
            ScreeningDto screeningDto = screeningDtoFromData(movieTitle, roomName, startTime);
            screeningService.createScreening(screeningDto);
        } catch (OverlappingScreeningException exception) {
            System.out.println("There is an overlapping screening");
        } catch (OverlappingScreeningBreakTimeException exception) {
            System.out.println("This would start in the break period after another screening in this room");
        }
    }


    @ShellMethodAvailability("isAdmin")
    @ShellMethod(value = "Delete a screening", key = "delete screening")
    void deleteScreening(String movieTitle, String roomName, String startTime) {
        ScreeningDto screeningDto = screeningDtoFromData(movieTitle, roomName, startTime);
        screeningService.deleteScreening(screeningDto);
    }

    public Availability isAdmin() {
        Optional<UserDto> userDto = userService.describe();
        return userDto.isPresent() && userDto.get().getRoles().contains(Role.ADMIN)
                ? Availability.available()
                : Availability.unavailable("User is not an admin");
    }

    public Availability isUser() {
        Optional<UserDto> userDto = userService.describe();
        return userDto.isPresent() && userDto.get().getRoles().contains(Role.USER)
                ? Availability.available()
                : Availability.unavailable("User is not an admin");
    }

    private ScreeningDto screeningDtoFromData(String movieTitle, String roomName, String startTime) {
        return new ScreeningDto(
                movieTitle,
                roomName,
                LocalDateTime.parse(startTime, dateTimeFormatter));
    }

}
