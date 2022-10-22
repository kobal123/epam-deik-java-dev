package com.epam.training.ticketservice.user;

import com.epam.training.ticketservice.booking.Booking;
import com.epam.training.ticketservice.booking.BookingRepository;
import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.movie.MovieRepository;
import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.room.RoomRepository;
import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {

    private UserRepository userRepository;
    private RoomRepository roomRepository;
    private BookingRepository bookingRepository;
    private MovieRepository movieRepository;
    private ScreeningRepository screeningRepository;
    private DateTimeFormatter dateTimeFormatter;

    public DataLoader(UserRepository userRepository, RoomRepository roomRepository, BookingRepository bookingRepository, MovieRepository movieRepository, ScreeningRepository screeningRepository, DateTimeFormatter dateTimeFormatter) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public void run(ApplicationArguments args) {

    }
}