package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.pricecomponent.PriceComponent;
import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.room.RoomDto;
import com.epam.training.ticketservice.room.RoomService;
import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningConverter;
import com.epam.training.ticketservice.screening.ScreeningDto;
import com.epam.training.ticketservice.screening.ScreeningRepository;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.seat.Seat;
import com.epam.training.ticketservice.seat.SeatDto;
import com.epam.training.ticketservice.user.UserDto;
import com.epam.training.ticketservice.user.UserRepository;
import com.epam.training.ticketservice.user.UserService;
import com.epam.training.ticketservice.user.model.User;
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
            Long totalPrice = calculateTotalPrice(bookingSeats, screeningToUpdate);

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
    public Long preCheckBookingPrice(ScreeningDto screening, Set<SeatDto> seats) {

        Optional<Screening> screeningOptional = screeningRepository.findScreeningByMovieAndRoomAndStartTime(
                screening.getMovieTitle(),
                screening.getRoomName(),
                screening.getStartTime());

        if (screeningOptional.isEmpty()) {
            throw new IllegalArgumentException("Screening was not found!");
        } else {

            Screening screeningToUpdate = screeningOptional.get();
            Set<Seat> bookingSeats = seats.stream().map(this::convertSeatDtoToEntity).collect(Collectors.toSet());

            return calculateTotalPrice(bookingSeats, screeningToUpdate);
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

    private Long calculateTotalPrice(Set<Seat> seats, Screening screening) {
        Movie movie = screening.getMovie();
        Room room = screening.getRoom();
        Long moviePriceTotal = movie.getPriceComponents().stream().mapToLong(PriceComponent::getPrice).sum();
        Long roomPriceTotal = room.getPriceComponents().stream().mapToLong(PriceComponent::getPrice).sum();
        Long screeningPriceTotal = screening.getPriceComponents().stream().mapToLong(PriceComponent::getPrice).sum();
        Long ticketPrice = screening.getTicketPrice();

        return seats.size() * (moviePriceTotal + roomPriceTotal + screeningPriceTotal + ticketPrice);
    }
}
