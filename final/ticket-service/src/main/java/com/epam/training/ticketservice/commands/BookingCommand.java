package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.booking.Booking;
import com.epam.training.ticketservice.booking.BookingService;
import com.epam.training.ticketservice.booking.Seat;
import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.security.SecurityContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ShellComponent
public class BookingCommand {
    private BookingService bookingService;
    private ScreeningService screeningService;
    private DateTimeFormatter dateTimeFormatter;


    public BookingCommand(BookingService bookingService, ScreeningService screeningService, DateTimeFormatter dateTimeFormatter) {
        this.bookingService = bookingService;
        this.screeningService = screeningService;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @ShellMethod(key = "book")
    void book(String movieTitle, String roomName, String startTime, String seats) {
        Set<Seat> setOfSeats = parseSeats(seats);
        System.out.println("seats are " + setOfSeats);
        Booking booking = new Booking();
        booking.setUsername(SecurityContext.USER.getUser().get().getName());
//        booking.setMovieTitle(movieTitle);
//        booking.setRoomName(roomName);
//        booking.setStartTime(LocalDateTime.parse(startTime,dateTimeFormatter));
        booking.setScreeningg(new Screening(movieTitle, roomName, LocalDateTime.parse(startTime,dateTimeFormatter)));
        for (Seat seat : setOfSeats) {
            seat.setBooking(booking);
        }
        booking.setSeats(setOfSeats);
        bookingService.createBooking(booking);
    }


    Set<Seat> parseSeats(String input) {
        String[] seatRowCol = input.split(" ");
        Set<Seat> seats = new HashSet<>();
        for (String position : seatRowCol) {
            String[] rowAndCol = position.split(",");
            int row = Integer.parseInt(rowAndCol[0]);
            int col = Integer.parseInt(rowAndCol[1]);
            seats.add(new Seat(row, col));
        }
        System.out.println("Seats: " + seats);
        return seats;
    }
}
