package com.epam.training.ticketservice.core.configuration;

import javax.annotation.PostConstruct;

import com.epam.training.ticketservice.core.bookingprice.BookingPrice;
import com.epam.training.ticketservice.core.bookingprice.BookingPriceRepository;
import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.movie.MovieRepository;
import com.epam.training.ticketservice.core.room.Room;
import com.epam.training.ticketservice.core.room.RoomRepository;
import com.epam.training.ticketservice.core.screening.Screening;
import com.epam.training.ticketservice.core.screening.ScreeningRepository;
import com.epam.training.ticketservice.core.user.UserRepository;
import com.epam.training.ticketservice.core.user.model.Role;
import com.epam.training.ticketservice.core.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Component
@Profile("!ci")
@RequiredArgsConstructor
public class ProdDatabaseInitializer {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final DateTimeFormatter dateTimeFormatter;
    private final BookingPriceRepository bookingPriceRepository;


    @PostConstruct
    public void init() {
        if (userRepository.findByName("admin").isEmpty()) {
            User admin = new User("admin", "admin", Set.of(Role.ADMIN));
            userRepository.save(admin);
        }
        BookingPrice bookingPrice = new BookingPrice();

        if (bookingPriceRepository.findById(1L).isEmpty()) {
            bookingPriceRepository.save(bookingPrice);
        }
    }
}