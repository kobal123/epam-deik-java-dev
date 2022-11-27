package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningConverter;
import com.epam.training.ticketservice.screening.ScreeningDto;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.screening.exception.ScreeningNotFoundException;
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

        Optional<Screening> screeningOptional = screeningService.getScreeningByMovieAndRoomAndStartTime(
                screening.getMovieTitle(),
                screening.getRoomName(),
                screening.getStartTime());

        if (screeningOptional.isEmpty()) {
            throw new IllegalArgumentException("Screening was not found!");
        } else {
            UserDto userDto = userService.describe().get();
            User user = userRepository.findByName(userDto.getName()).orElseThrow(
                    () -> new IllegalArgumentException("No such username!"));
            Booking booking = new Booking();
            Screening screeningToUpdate = screeningOptional.get();
            Set<Seat> bookingSeats = seats.stream().map(this::convertSeatDtoToEntity).collect(Collectors.toSet());
            booking.setUser(user);
            booking.setScreening(screeningToUpdate);
            booking.setSeats(bookingSeats);
            Long totalPrice = 0L;

            for (Seat seat : booking.getSeats()) {
                seat.setScreening(screeningToUpdate);
                totalPrice += screeningToUpdate.getTicketPrice();
            }
            booking.setPriceTotal(totalPrice);
            screeningToUpdate.addBooking(booking);
            //screeningService.updateScreening(screeningToUpdate);
            //bookingRepository.save(booking);
            return convertBookingToDto(booking);
        }
    }



    public List<BookingDto> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::convertBookingToDto)
                .collect(Collectors.toList());
    }

    private BookingDto convertBookingToDto(Booking booking) {
        Set<SeatDto> seats = booking.getSeats()
                .stream()
                .map(this::convertSeatDtoToEntity)
                .collect(Collectors.toSet());
        ScreeningDto screeningDto = screeningConverter.toDto(booking.getScreening());
        return new BookingDto(screeningDto, seats, booking.getPriceTotal());
    }


    private SeatDto convertSeatDtoToEntity(Seat seat){
        return new SeatDto(seat.getSeatRow(), seat.getSeatCol());
    }

    private Seat convertSeatDtoToEntity(SeatDto seat){
        return new Seat(seat.getSeatRow(), seat.getSeatCol());
    }

}
