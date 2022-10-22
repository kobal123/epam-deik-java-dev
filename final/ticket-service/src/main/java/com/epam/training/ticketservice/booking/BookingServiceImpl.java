package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService{

    private BookingRepository bookingRepository;
    private UserRepository userRepository;

    private final DateTimeFormatter formatter;


    public BookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository, DateTimeFormatter formatter) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.formatter = formatter;
    }



    @Override
    public List<Booking> getBookingsByUsername(String username) {
        return bookingRepository.getBookingByUsername(username);
    }

    @Override
    public void createBooking(Booking booking) {


        bookingRepository.save(booking);
    }
}
