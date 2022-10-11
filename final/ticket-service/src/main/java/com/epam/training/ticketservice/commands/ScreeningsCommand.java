package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ShellComponent
public class ScreeningsCommand {

    private final ScreeningService screeningService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ScreeningsCommand(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @ShellMethod(value = "List all screenings", key = "list screenings")
    void ListScreenings() {

    }



    @ShellMethod(value = "Create a new screening", key = "create screening")
    void createScreening(String movieTitle, String roomName, String startTime) {
        //Screening screening = new Screening(movieTitle,roomName, LocalDateTime.parse(startTime,formatter));
        screeningService.createScreening(movieTitle,roomName,startTime);
    }

    @ShellMethod(value = "Update a screening", key = "update screening")
    void updateScreening(String movieTitle, String roomName, String startTime) {

    }

    @ShellMethod(value = "Delete a screening", key = "delete screening")
    void deleteScreening(String movieTitle, String roomName, String startTime) {

    }
}
