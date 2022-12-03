package com.epam.training.ticketservice.core.booking;

import com.epam.training.ticketservice.core.bookingprice.BookingPriceService;
import com.epam.training.ticketservice.core.bookingprice.calculator.PriceCalculator;
import com.epam.training.ticketservice.core.pricecomponent.PriceComponent;
import com.epam.training.ticketservice.core.room.Room;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.screening.ScreeningConverter;
import com.epam.training.ticketservice.core.screening.ScreeningDto;
import com.epam.training.ticketservice.core.screening.ScreeningRepository;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.seat.Seat;
import com.epam.training.ticketservice.core.seat.SeatDto;
import com.epam.training.ticketservice.core.user.UserDto;
import com.epam.training.ticketservice.core.user.UserRepository;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.screening.Screening;
import com.epam.training.ticketservice.core.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;
    private UserService userService;
    private UserRepository userRepository;
    private ScreeningService screeningService;
    private RoomService roomService;
    private ScreeningRepository screeningRepository;
    private final DateTimeFormatter formatter;
    private final ScreeningConverter screeningConverter;
    private final BookingPriceService bookingPriceService;


    @Override
    public List<BookingDto> getBookingsByUsername(String username) {
        return bookingRepository.findBookingByUserName(username)
                .stream()
                .map(this::convertBookingToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookingDto createBooking(ScreeningDto screening, Set<SeatDto> seats) {

        Optional<Screening> screeningOptional = screeningRepository.findScreeningByMovieAndRoomAndStartTime(
                screening.getMovieTitle(),
                screening.getRoomName(),
                screening.getStartTime());

        if (screeningOptional.isEmpty()) {
            throw new IllegalArgumentException("Screening was not found!");
        } else {
            UserDto userDto = userService.describe().get();
            User user = userRepository.findByName(userDto.getName())
                    .orElseThrow(() -> new IllegalArgumentException("No such username!"));

            Screening screeningToUpdate = screeningOptional.get();
            Set<Seat> bookingSeats = seats.stream().map(this::convertSeatDtoToEntity).collect(Collectors.toSet());
            Booking booking = new Booking();
            booking.setUser(user);
            booking.setScreening(screeningToUpdate);
            booking.setSeats(bookingSeats);

            Long totalPrice = bookingPriceService.getBookingPrice(screeningToUpdate, bookingSeats);

            for (Seat seat : bookingSeats) {
                seat.setScreening(screeningToUpdate);
            }

            booking.setPriceTotal(totalPrice);
            screeningToUpdate.addBooking(booking);
            return convertBookingToDto(booking);
        }
    }



    public List<BookingDto> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::convertBookingToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long preCheckBookingPrice(ScreeningDto screeningDto, Set<SeatDto> seats) {

        Optional<Screening> screeningOptional = screeningRepository.findScreeningByMovieAndRoomAndStartTime(
                screeningDto.getMovieTitle(),
                screeningDto.getRoomName(),
                screeningDto.getStartTime());

        if (screeningOptional.isEmpty()) {
            throw new IllegalArgumentException("Screening was not found!");
        } else {

            Screening screening = screeningOptional.get();
            Set<Seat> bookingSeats = seats.stream().map(this::convertSeatDtoToEntity).collect(Collectors.toSet());

            return bookingPriceService.getBookingPrice(screening, bookingSeats);
        }
    }

    private BookingDto convertBookingToDto(Booking booking) {
        Set<SeatDto> seats = booking.getSeats()
                .stream()
                .map(this::convertSeatDtoToEntity)
                .collect(Collectors.toSet());
        ScreeningDto screeningDto = screeningConverter.toDto(booking.getScreening());
        return new BookingDto(screeningDto, seats, booking.getPriceTotal());
    }


    private SeatDto convertSeatDtoToEntity(Seat seat) {
        return new SeatDto(seat.getSeatRow(), seat.getSeatCol());
    }

    private Seat convertSeatDtoToEntity(SeatDto seat) {
        return new Seat(seat.getSeatRow(), seat.getSeatCol());
    }

}
