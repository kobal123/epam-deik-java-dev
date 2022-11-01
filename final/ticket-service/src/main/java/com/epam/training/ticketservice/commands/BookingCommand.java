package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.booking.*;
import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningRepository;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.security.UserContext;
import com.epam.training.ticketservice.user.model.User;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@ShellComponent
public class BookingCommand {
    private BookingService bookingService;
    private DateTimeFormatter dateTimeFormatter;

    public BookingCommand(BookingService bookingService, ScreeningService screeningService, DateTimeFormatter dateTimeFormatter, ScreeningRepository screeningRepository) {
        this.bookingService = bookingService;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @ShellMethod(key = "book")
    void book(String movieTitle, String roomName, String startTime, String seats) {

        User user = UserContext.getUser()
                .orElseThrow(() -> new RuntimeException("Booking failed, could not find user."));

        Screening screening = new Screening();
        screening.setMovieTitle(movieTitle);
        screening.setRoomName(roomName);
        screening.setStartTime(LocalDateTime.parse(startTime,dateTimeFormatter));

        Set<Seat> setOfSeats = parseSeats(seats, screening);

        Booking booking = new Booking();
        booking.setUsername(user.getName());
        booking.setScreening(screening);
        booking.setSeats(setOfSeats);

        bookingService.createBooking(booking);
    }


    private Set<Seat> parseSeats(String input, Screening screening) {
        String[] seatRowCol = input.split(" ");
        Set<Seat> seats = new HashSet<>();
        for (String position : seatRowCol) {
            String[] rowAndCol = position.split(",");
            int row = Integer.parseInt(rowAndCol[0]);
            int col = Integer.parseInt(rowAndCol[1]);

            Seat seat = new Seat();
            seat.setSeatCol(col);
            seat.setSeatRow(row);
            seats.add(seat);
        }
        return seats;
    }


}
