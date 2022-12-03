package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.booking.BookingDto;
import com.epam.training.ticketservice.booking.BookingService;
import com.epam.training.ticketservice.seat.SeatDto;
import com.epam.training.ticketservice.seat.SeatServiceImpl;
import com.epam.training.ticketservice.bookingprice.BookingPriceService;
import com.epam.training.ticketservice.screening.ScreeningDto;
import com.epam.training.ticketservice.user.UserDto;
import com.epam.training.ticketservice.user.UserService;
import com.epam.training.ticketservice.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

@ShellComponent
@RequiredArgsConstructor
public class BookingCommand {
    private final BookingService bookingService;
    private final DateTimeFormatter dateTimeFormatter;
    private final UserService userService;
    private final SeatServiceImpl seatServiceImpl;
    private final BookingPriceService bookingPriceService;
    private final String bookingMessageFormat = "Seats booked: %s; the price for this booking is %d HUF";
    private final String priceCheckMessageFormat = "The price for this booking would be %d HUF";

    private final String seatFormat = "(%d,%d)";

    @ShellMethodAvailability("isUser")
    @ShellMethod(key = "book")
    void book(String movieTitle, String roomName, String startTime, String seats) {

        ScreeningDto screening = new ScreeningDto(
                movieTitle,
                roomName,
                LocalDateTime.parse(startTime, dateTimeFormatter)
        );
        List<SeatDto> setOfSeats = parseSeats(seats);
        List<SeatDto> alreadyBookedSeats = seatServiceImpl.getSeatsForScreening(screening);

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
        System.out.println(String.format(bookingMessageFormat, joiner, booking.getTotalPrice()));

    }


    @ShellMethod(key = "show price for")
    void showPrice(String movieTitle, String roomName, String startTime, String seats) {

        ScreeningDto screening = new ScreeningDto(
                movieTitle,
                roomName,
                LocalDateTime.parse(startTime, dateTimeFormatter)
        );
        List<SeatDto> setOfSeats = parseSeats(seats);

        Long totalPrice = bookingService.preCheckBookingPrice(screening, new HashSet<>(setOfSeats));

        System.out.println(String.format(priceCheckMessageFormat, totalPrice));

    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "update base price")
    void updateBasePrice(Long price) {
        bookingPriceService.updateBasePrice(price);
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

    public Availability isUser() {
        Optional<UserDto> userDto = userService.describe();
        return userDto.isPresent() && userDto.get().getRoles().contains(Role.USER)
                ? Availability.available()
                : Availability.unavailable("User is not logged in");
    }

    public Availability isAdmin() {
        Optional<UserDto> userDto = userService.describe();
        return userDto.isPresent() && userDto.get().getRoles().contains(Role.ADMIN)
                ? Availability.available()
                : Availability.unavailable("User is not an admin");
    }
}
