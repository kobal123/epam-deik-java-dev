package com.epam.training.ticketservice;

import com.epam.training.ticketservice.booking.BookingRepository;
import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.movie.MovieRepository;
import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.room.RoomRepository;
import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningRepository;
import com.epam.training.ticketservice.user.Role;
import com.epam.training.ticketservice.user.User;
import com.epam.training.ticketservice.user.UserRepository;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@SpringBootApplication
public class Application {

    static ApplicationContext applicationContext;
    public static void main(String[] args) {
        System.out.println("Hello world");
        SpringApplication app = new SpringApplication(Application.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.setBannerMode(Banner.Mode.OFF);

        app.run(args);

    }



    @Component
    public class DataLoader implements ApplicationRunner {
        private ApplicationContext applicationContext;
        private UserRepository userRepository;
        private RoomRepository roomRepository;
        private BookingRepository bookingRepository;
        private MovieRepository movieRepository;
        private ScreeningRepository screeningRepository;
        private DateTimeFormatter dateTimeFormatter;

        public DataLoader(ApplicationContext applicationContext, UserRepository userRepository, RoomRepository roomRepository, BookingRepository bookingRepository, MovieRepository movieRepository, ScreeningRepository screeningRepository, DateTimeFormatter dateTimeFormatter) {
            this.applicationContext = applicationContext;
            this.userRepository = userRepository;
            this.roomRepository = roomRepository;
            this.bookingRepository = bookingRepository;
            this.movieRepository = movieRepository;
            this.screeningRepository = screeningRepository;
            this.dateTimeFormatter = dateTimeFormatter;
        }

        public void run(ApplicationArguments args) {

        }

        @EventListener(ContextRefreshedEvent.class)
        public void doSomethingAfterStartup() throws SQLException {
            System.out.println("ASLDKMASLKDMALSKDM");
            userRepository.save(new User("admin","admin", Set.of(Role.ADMIN)));
            userRepository.save(new User("s","a", Set.of(Role.USER)));
            Room room = new Room("Pedersoli",20,10);
            Movie movie = new Movie("Sátántangó","drama",135);
            roomRepository.save(room);
            movieRepository.save(movie);
            screeningRepository.save(new Screening("Sátántangó", "Pedersoli", LocalDateTime.parse("2021-03-15 10:45",dateTimeFormatter)));
            //org.h2.tools.Server.startWebServer(((DataSource)applicationContext.getBean("dataSource")).getConnection());

        }
    }
}



/*
*
* @EventListener(ContextRefreshedEvent.class)
        public void doSomethingAfterStartup() {
            System.out.println("Ááááááááááá");
            userRepository.save(new User("admin","admin", Set.of(Role.ADMIN,Role.USER)));
        }
*
* */