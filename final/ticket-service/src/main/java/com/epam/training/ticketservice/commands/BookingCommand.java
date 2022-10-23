package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.booking.*;
import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningId;
import com.epam.training.ticketservice.screening.ScreeningRepository;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.security.SecurityContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@ShellComponent
public class BookingCommand {
    private BookingService bookingService;
    private ScreeningService screeningService;
    private DateTimeFormatter dateTimeFormatter;
    private ScreeningRepository screeningRepository;

    public BookingCommand(BookingService bookingService, ScreeningService screeningService, DateTimeFormatter dateTimeFormatter, ScreeningRepository screeningRepository) {
        this.bookingService = bookingService;
        this.screeningService = screeningService;
        this.dateTimeFormatter = dateTimeFormatter;
        this.screeningRepository = screeningRepository;
    }

    @ShellMethod(key = "book")
    void book(String movieTitle, String roomName, String startTime, String seats) {
        ScreeningId screeningId = new ScreeningId(movieTitle, roomName, LocalDateTime.parse(startTime,dateTimeFormatter));

        Screening screening = new Screening(screeningId);

        BookingId bookingId = new BookingId(SecurityContext.USER.getUser().get().getName(),screeningId);
        Booking booking = new Booking();
        booking.setUsername(SecurityContext.USER.getUser().get().getName());
        Set<Seat> setOfSeats = parseSeats(seats,booking,screening);

        booking.setScreeningg(screening);
        booking.setSeats(setOfSeats);
        screeningRepository.save(screening);
        bookingService.createBooking(booking);
    }


    Set<Seat> parseSeats(String input, Booking booking,Screening screening) {
        String[] seatRowCol = input.split(" ");
        Set<Seat> seats = new HashSet<>();
        for (String position : seatRowCol) {
            String[] rowAndCol = position.split(",");
            int row = Integer.parseInt(rowAndCol[0]);
            int col = Integer.parseInt(rowAndCol[1]);

            Seat seat = new Seat();
            seat.setSeatCol(col);
            seat.setSeatRow(row);
            seat.setBooking(booking);
            seat.setScreening(screening);
            seats.add(seat);
        }
        System.out.println("Seats: " + seats);
        return seats;
    }
}
