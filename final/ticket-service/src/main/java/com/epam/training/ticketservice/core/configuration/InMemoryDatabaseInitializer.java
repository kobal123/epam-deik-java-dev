package com.epam.training.ticketservice.core.configuration;

import javax.annotation.PostConstruct;

import com.epam.training.ticketservice.core.bookingprice.BookingPrice;
import com.epam.training.ticketservice.core.bookingprice.BookingPriceRepository;
import com.epam.training.ticketservice.core.movie.MovieRepository;
import com.epam.training.ticketservice.core.room.RoomRepository;
import com.epam.training.ticketservice.core.screening.ScreeningRepository;
import com.epam.training.ticketservice.core.user.UserRepository;
import com.epam.training.ticketservice.core.user.model.Role;
import com.epam.training.ticketservice.core.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Set;

@Component
@Profile("ci")
@RequiredArgsConstructor
public class InMemoryDatabaseInitializer {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final DateTimeFormatter dateTimeFormatter;
    private final BookingPriceRepository bookingPriceRepository;


    @PostConstruct
    public void init() {
        User admin = new User("admin", "admin", Set.of(Role.ADMIN));
        BookingPrice bookingPrice = new BookingPrice();
        userRepository.save(admin);
        bookingPriceRepository.save(bookingPrice);
        //Room room = new Room("Pedersoli",20,10);
        //Movie movie = new Movie("Sátántangó","drama",135);
        //roomRepository.save(room);
        //movieRepository.save(movie);
        //LocalDateTime time = LocalDateTime.parse("2021-03-15 10:45", dateTimeFormatter);
        //Screening screening = new Screening(movie, room, time);
        //screening.setBookingPrice(bookingPrice);

        //screeningRepository.save(screening);

    }
}