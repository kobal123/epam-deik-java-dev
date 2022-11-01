package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningId;
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
        ScreeningId screeningId = booking.getScreening().getScreeningId();
        Optional<Screening> screeningOptional = screeningService.getScreeningById(screeningId);
        if (screeningOptional.isEmpty()) {
            throw new ScreeningNotFoundException("No screening was found with id " + screeningId);
        } else{
            Screening screening = screeningOptional.get();
            screening.addBooking(booking);

            screeningService.updateScreening(screening);
        }
    }


}
