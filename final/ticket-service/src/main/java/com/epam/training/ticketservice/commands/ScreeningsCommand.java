package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.movie.MovieService;
import com.epam.training.ticketservice.screening.OverlappingScreeningBreakTimeException;
import com.epam.training.ticketservice.screening.OverlappingScreeningException;
import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.security.SecurityContext;
import com.epam.training.ticketservice.user.Role;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.text.Collator;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@ShellComponent
public class ScreeningsCommand {

    private final ScreeningService screeningService;
    private final MovieService movieService;
    private final DateTimeFormatter dateTimeFormatter;

    public ScreeningsCommand(ScreeningService screeningService,
                             MovieService movieService,
                             DateTimeFormatter dateTimeFormatter) {
        this.screeningService = screeningService;
        this.movieService = movieService;
        this.dateTimeFormatter = dateTimeFormatter;
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
            screeningService.createScreening(movieTitle,roomName,startTime);
        } catch (OverlappingScreeningException exception) {
            System.out.println("There is an overlapping screening");
        } catch (OverlappingScreeningBreakTimeException exception) {
            System.out.println("This would start in the break period after another screening in this room");
        }
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(value = "Update a screening", key = "update screening")
    void updateScreening(String movieTitle, String roomName, String startTime) {
        screeningService.updateScreening(movieTitle,roomName,startTime);
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(value = "Delete a screening", key = "delete screening")
    void deleteScreening(String movieTitle, String roomName, String startTime) {
        screeningService.deleteScreening(movieTitle,roomName,startTime);
    }

    @ShellMethodAvailability({"deleteScreening","updateScreening","createScreening"})
    public Availability isAdmin() {
        return SecurityContext.USER.currentUserHasRole(Role.ADMIN)
                ? Availability.available()
                : Availability.unavailable("User is not an admin");
    }

    public Availability isBasicUser() {
        return SecurityContext.USER.currentUserHasRole(Role.USER)
                ? Availability.available()
                : Availability.unavailable("User is not a basic user.");
    }

}
