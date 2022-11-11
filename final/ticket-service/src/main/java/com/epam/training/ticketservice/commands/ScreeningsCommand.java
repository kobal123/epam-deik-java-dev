package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.movie.MovieService;
import com.epam.training.ticketservice.screening.exception.OverlappingScreeningBreakTimeException;
import com.epam.training.ticketservice.screening.exception.OverlappingScreeningException;
import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.user.UserDTO;
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
        List<Screening> screenings = screeningService.getAllScreenings();
        screenings.sort(Comparator.comparing(Screening::getMovieTitle,Collator.getInstance()));
        if (screenings.isEmpty()) {
            System.out.println("There are no screenings");
        }

        String format = "%s (%s, %d minutes), screened in room %s, at %s";
        for (Screening screening : screenings) {
            Movie movie = movieService.getMovieByName(screening.getMovieTitle())
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
            Screening screening = new Screening();
            screening.setRoomName(roomName);
            screening.setMovieTitle(movieTitle);
            screening.setStartTime(LocalDateTime.parse(startTime,dateTimeFormatter));
            screeningService.createScreening(screening);
        } catch (OverlappingScreeningException exception) {
            System.out.println("There is an overlapping screening");
        } catch (OverlappingScreeningBreakTimeException exception) {
            System.out.println("This would start in the break period after another screening in this room");
        }
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(value = "Update a screening", key = "update screening")
    void updateScreening(String movieTitle, String roomName, String startTime) {
        Screening screening = new Screening();
        screening.setMovieTitle(movieTitle);
        screening.setRoomName(roomName);
        screening.setStartTime(LocalDateTime.parse(startTime, dateTimeFormatter));
        screeningService.updateScreening(screening);
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(value = "Delete a screening", key = "delete screening")
    void deleteScreening(String movieTitle, String roomName, String startTime) {
        screeningService.deleteScreening(movieTitle,roomName,startTime);
    }

    public Availability isAdmin() {
        Optional<UserDTO> userDTO = userService.describe();
        return userDTO.isPresent() && userDTO.get().hasRole(Role.ADMIN)
                ? Availability.available()
                : Availability.unavailable("User is not an admin");
    }

    public Availability isUser() {
        Optional<UserDTO> userDTO = userService.describe();
        return userDTO.isPresent() && userDTO.get().hasRole(Role.USER)
                ? Availability.available()
                : Availability.unavailable("User is not an admin");
    }

}
