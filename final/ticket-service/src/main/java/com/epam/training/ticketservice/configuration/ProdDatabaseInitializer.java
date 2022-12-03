package com.epam.training.ticketservice.configuration;

import javax.annotation.PostConstruct;

import com.epam.training.ticketservice.bookingprice.BookingPrice;
import com.epam.training.ticketservice.bookingprice.BookingPriceRepository;
import com.epam.training.ticketservice.movie.MovieRepository;
import com.epam.training.ticketservice.room.RoomRepository;
import com.epam.training.ticketservice.screening.ScreeningRepository;
import com.epam.training.ticketservice.user.UserRepository;
import com.epam.training.ticketservice.user.model.Role;
import com.epam.training.ticketservice.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

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