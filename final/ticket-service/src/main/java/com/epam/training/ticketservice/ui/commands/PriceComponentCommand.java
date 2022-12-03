package com.epam.training.ticketservice.ui.commands;


import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.pricecomponent.PriceComponentService;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.screening.ScreeningDto;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.user.UserDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class PriceComponentCommand {


    private final MovieService movieService;
    private final UserService userService;
    private final RoomService roomService;
    private final ScreeningService screeningService;
    private final PriceComponentService priceComponentService;
    private final DateTimeFormatter dateTimeFormatter;

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(value = "Create a new price component", key = "create price component")
    public void createPriceComponent(String name, Integer price) {
        priceComponentService.createPriceComponent(name,price);
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(value = "attach a component to a room", key = "attach price component to room")
    public void attachToRoom(String componentName, String roomName) {

        roomService.attachPriceComponent(componentName, roomName);
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(value = "attach a component to a movie", key = "attach price component to movie")
    public void attachToMovie(String componentName, String movieName) {
        movieService.attachPriceComponent(componentName, movieName);
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(value = "attach a component to a screening", key = "attach price component to screening")
    public void attachToScreening(String componentName, String movieName, String roomName, String startTime) {
        ScreeningDto screeningDto = new ScreeningDto(
                movieName,
                roomName,
                LocalDateTime.parse(startTime, dateTimeFormatter)
        );
        screeningService.attachPriceComponent(componentName, screeningDto);
    }

    public Availability isAdmin() {
        Optional<UserDto> userDto = userService.describe();
        return userDto.isPresent() && userDto.get().getRoles().contains(Role.ADMIN)
                ? Availability.available()
                : Availability.unavailable("User is not an admin");
    }
}
