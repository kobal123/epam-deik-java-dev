package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.booking.Booking;
import com.epam.training.ticketservice.booking.BookingService;
import com.epam.training.ticketservice.security.SecurityContext;
import com.epam.training.ticketservice.user.*;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class UserCommand {
    private final UserService userService;
    private final String loginFailedMessage = "Login failed due to incorrect credentials";
    private final BookingService bookingService;
    public UserCommand(UserService userService, BookingService bookingService) {
        this.userService = userService;
        this.bookingService = bookingService;
    }

    @ShellMethod(value = "Sign in as a privileged user", key = "sign in privileged")
    public void signInPrivileged(String username,String password) {
        try {
            userService.signInPrivilegedUser(username,password);
        } catch (BadCredentialsException badCredentialsException) {
            System.out.println(loginFailedMessage);
        } catch (UserPrivilegeException userPrivilegeException) {
            System.out.println(userPrivilegeException.getMessage());
        }
    }



    @ShellMethod(value = "Sign out from current account", key = "sign out")
    public void signOut() {
        userService.signOutUser();
    }


    @ShellMethod(value = "Sign in as a basic user", key = "sign in")
    public void signIn(String username,String password) {
        try {
            userService.signInBasicUser(username,password);
        } catch (BadCredentialsException badCredentialsException) {
            System.out.println(loginFailedMessage);
        } catch (UserPrivilegeException userPrivilegeException) {
            System.out.println(userPrivilegeException.getMessage());
        }
    }


    @ShellMethod(value = "Register a new user", key = "sign up")
    public void signUp(String username, String password) {
        try {
            userService.registerUser(username, password);
            System.out.println("Created user " + username);
        } catch (BadCredentialsException exception) {
            System.out.println("Login failed due to incorrect credentials");
        }
    }


    @ShellMethod(value = "Gives information about the currently logged in account", key = "describe account")
    public void
    describeAccount() {
        if (SecurityContext.USER.isUserLoggedIn()) {
            User user = SecurityContext.USER.getUser().get();

            if (SecurityContext.USER.currentUserHasRole(Role.ADMIN)) {
                System.out.println(String.format("Signed in with privileged account '%s'",
                        user.getName()));
            } else if (SecurityContext.USER.currentUserHasRole(Role.USER)) {
                System.out.println(String.format("Signed in with account '%s'",
                        user.getName()));

                List<Booking> bookings = bookingService.getBookingsByUsername(user.getName());
                if (bookings.isEmpty()) {
                    System.out.println("You have not booked any tickets yet");
                } else {
                    displayBookings(bookings);
                }

            }
        } else {
            System.out.println("You are not signed in");
        }
    }

    private void displayBookings(List<Booking> bookings) {
        System.out.println("Your previous bookings are");
        String bookingFormat = "Seats %s, %s on %s in room %s starting at %s for %i HUF";
        bookings.forEach(System.out::println);
        bookings.stream().map(Booking::getScreeningg).forEach(System.out::println);
    }
}
