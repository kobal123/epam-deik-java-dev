package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.booking.BookingDto;
import com.epam.training.ticketservice.booking.BookingService;
import com.epam.training.ticketservice.booking.Seat;
import com.epam.training.ticketservice.booking.SeatDto;
import com.epam.training.ticketservice.booking.SeatService;
import com.epam.training.ticketservice.screening.ScreeningDto;
import com.epam.training.ticketservice.screening.ScreeningRepository;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

@ShellComponent
@RequiredArgsConstructor
public class BookingCommand {
    private final BookingService bookingService;
    private final DateTimeFormatter dateTimeFormatter;
    private final UserService userService;
    private final SeatService seatService;
    private final String messageFormat = "Seats booked: %s; the price for this booking is %d HUF";
    private final String seatFormat = "(%d,%d)";

    @ShellMethod(key = "book")
    void book(String movieTitle, String roomName, String startTime, String seats) {

        ScreeningDto screening = new ScreeningDto(
                movieTitle,
                roomName,
                LocalDateTime.parse(startTime, dateTimeFormatter)
        );
        List<SeatDto> setOfSeats = parseSeats(seats);
        List<SeatDto> alreadyBookedSeats = seatService.getSeatsForScreening(screening);

        for (SeatDto seat : setOfSeats) {
            if (alreadyBookedSeats.contains(seat)) {
                String format = "Seat (%d,%d) is already taken";
                System.out.println(String.format(format, seat.getSeatRow(), seat.getSeatCol()));
                return;
            }
        }

        BookingDto booking = bookingService.createBooking(screening, new HashSet<>(setOfSeats));
        StringJoiner joiner = new StringJoiner(", ");
        for (SeatDto seat : setOfSeats) {
            joiner.add(String.format(seatFormat, seat.getSeatRow(), seat.getSeatCol()));
        }
        System.out.println(String.format(messageFormat, joiner, booking.getTotalPrice()));

    }

    private List<SeatDto> parseSeats(String input) {
        String[] seatRowCol = input.split(" ");
        List<SeatDto> seats = new ArrayList<>();
        for (String position : seatRowCol) {
            String[] rowAndCol = position.split(",");
            int row = Integer.parseInt(rowAndCol[0]);
            int col = Integer.parseInt(rowAndCol[1]);

            SeatDto seat = new SeatDto(row, col);
            seats.add(seat);
        }
        return seats;
    }
}
