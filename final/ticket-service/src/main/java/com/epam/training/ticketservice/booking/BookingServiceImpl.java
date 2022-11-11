package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.screening.exception.ScreeningNotFoundException;
import com.epam.training.ticketservice.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService{

    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private ScreeningService screeningService;
    private final DateTimeFormatter formatter;


    @Override
    @Transactional
    public List<Booking> getBookingsByUsername(String username) {
        return bookingRepository.getBookingByUsername(username);
    }

    @Override
    @Transactional
    public void createBooking(Booking booking) {
        Screening screening = booking.getScreening();
        Optional<Screening> screeningOptional = screeningService.getScreeningByMovieAndRoomAndStartTime(
                screening.getMovieTitle(),
                screening.getRoomName(),
                screening.getStartTime());


        if (screeningOptional.isEmpty()) {
            throw new ScreeningNotFoundException("No screening was found with id " + screening.getId());
        } else{
            Screening screeningToUpdate = screeningOptional.get();
            System.out.println("about to save booking, screening is " + screeningToUpdate);
            booking.setScreening(screeningToUpdate);
            booking.getSeats().forEach(seat -> seat.setScreening(screeningToUpdate));
            screeningToUpdate.addBooking(booking);
            screeningService.updateScreening(screeningToUpdate);
            //bookingRepository.save(booking);
        }
    }

    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

}
