package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.core.booking.BookingDto;
import com.epam.training.ticketservice.core.booking.BookingService;
import com.epam.training.ticketservice.core.seat.SeatDto;
import com.epam.training.ticketservice.core.seat.SeatRepository;
import com.epam.training.ticketservice.core.screening.ScreeningDto;
import com.epam.training.ticketservice.core.user.UserDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

@ShellComponent
@RequiredArgsConstructor
public class UserCommand {
    //private final UserServiceImpl userServiceImpl;
    private final String loginFailedMessage = "Login failed due to incorrect credentials";
    private final BookingService bookingService;
    private final SeatRepository seatRepository;
    private final UserService userService;
    private final DateTimeFormatter formatter;

    @ShellMethod(value = "Sign in as a privileged user", key = "sign in privileged")
    public void signInPrivileged(String username,String password) {
        Optional<UserDto> user = userService.login(username,password);
        if (user.isEmpty()) {
            System.out.println(loginFailedMessage);
        }
    }



    @ShellMethod(value = "Sign out from current account", key = "sign out")
    public void signOut() {
        userService.logout();
    }


    @ShellMethod(value = "Sign in as a basic user", key = "sign in")
    public void signIn(String username,String password) {
        Optional<UserDto> user = userService.login(username,password);
        if (user.isEmpty()) {
            System.out.println(loginFailedMessage);
        }
    }


    @ShellMethod(value = "Register a new user", key = "sign up")
    public String signUp(String username, String password) {

        try {
            userService.register(username, password);
            return String.format("Successfully registered user %s", username);
        } catch (DataIntegrityViolationException exception) {
            return String.format("Failed to  register user %s", username);
        }
    }


    @ShellMethod(value = "Gives information about the currently logged in account", key = "describe account")
    public void describeAccount() {
        Optional<UserDto> optionalUserDto = userService.describe();
        if (optionalUserDto.isEmpty()) {
            System.out.println("You are not signed in");
            return;
        }
        UserDto user = optionalUserDto.get();
        Set<Role> userRoles = user.getRoles();
        if (userRoles.contains(Role.ADMIN)) {
            System.out.println(String.format("Signed in with privileged account '%s'",
                    user.getName()));
        } else if (userRoles.contains(Role.USER)) {
            System.out.println(String.format("Signed in with account '%s'",
                    user.getName()));

            List<BookingDto> bookings = bookingService.getBookingsByUsername(user.getName());
            if (bookings.isEmpty()) {
                System.out.println("You have not booked any tickets yet");
            } else {
                displayBookings(bookings);
            }
        }
    }

    private void displayBookings(List<BookingDto> bookings) {
        System.out.println("Your previous bookings are");
        String format = "Seats %s on %s in room %s starting at %s for %d HUF";
        for (BookingDto bookingDto : bookings) {

            StringJoiner joiner = new StringJoiner(", ");
            String seatFormat = "(%d,%d)";
            ScreeningDto screening = bookingDto.getScreeningDto();
            for (SeatDto seat : bookingDto.getSeats()) {
                joiner.add(String.format(seatFormat,seat.getSeatRow(),seat.getSeatCol()));
            }

            System.out.println(String.format(
                    format,
                    joiner,
                    screening.getMovieTitle(),
                    screening.getRoomName(),
                    formatter.format(screening.getStartTime()),
                    bookingDto.getTotalPrice()));
        }


    }
}
