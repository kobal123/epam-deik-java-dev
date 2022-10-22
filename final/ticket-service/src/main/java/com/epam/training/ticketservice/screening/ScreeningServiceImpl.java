package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.movie.MovieRepository;
import com.epam.training.ticketservice.room.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ScreeningServiceImpl implements ScreeningService {
    private static final int BREAK_TIME = 10;
    private final DateTimeFormatter formatter;
    private final ScreeningRepository screeningRepository;
    private final RoomRepository roomRepository;
    private final MovieRepository movieRepository;


    public ScreeningServiceImpl(DateTimeFormatter formatter,
                                ScreeningRepository screeningRepository,
                                RoomRepository roomRepository,
                                MovieRepository movieRepository) {
        this.formatter = formatter;
        this.screeningRepository = screeningRepository;
        this.roomRepository = roomRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public void createScreening(String movieName,String room, String startTime) {
        ScreeningId screeningId = new ScreeningId(movieName,room, LocalDateTime.parse(startTime,formatter));
        Optional<Screening> screeningOptional = screeningRepository.findById(screeningId);
        if (screeningOptional.isPresent()) {
            throw new RuntimeException("Screening already exists");
        }

        Screening screeningToSave = new Screening(screeningId);
        validateScreening(screeningToSave);
        screeningRepository.save(screeningToSave);
    }

    private void validateScreening(Screening screeningToSave) {
        List<Screening> screenings = screeningRepository.findAll();
        for (Screening screening : screenings) {
            if (screening.getRoomName().equals(screeningToSave.getRoomName())) {
                checkScreeningDateCollision(screening,screeningToSave);
            }
        }

    }

    private void checkScreeningDateCollision(Screening firstScreening, Screening secondScreening) {
        Movie firstMovie = movieRepository.findById(firstScreening.getMovieTitle()).get();
        Movie secondMovie = movieRepository.findById(secondScreening.getMovieTitle()).get();
        LocalDateTime firstStart = firstScreening.getStartTime();
        LocalDateTime secondStart = secondScreening.getStartTime();
        LocalDateTime firstEnd = firstStart.plusMinutes(firstMovie.getScreenTime());
        LocalDateTime secondEnd = secondStart.plusMinutes(secondMovie.getScreenTime());

        boolean isSecondStartBetweenScreeningPeriod = secondStart.isAfter(firstStart)
                && secondStart.isBefore(firstEnd);

        boolean isSecondEndBetweenScreeningPeriod = secondEnd.isAfter(firstStart)
                && secondEnd.isBefore(firstEnd);

        boolean isSecondStartInBreakTime = secondStart.isAfter(firstEnd)
                && secondStart.isBefore(firstEnd.plusMinutes(BREAK_TIME));


        if (isSecondStartBetweenScreeningPeriod && isSecondEndBetweenScreeningPeriod) {
            throw new OverlappingScreeningException();
        } else if (isSecondStartInBreakTime) {
            throw new OverlappingScreeningBreakTimeException();
        }

    }


    @Override
    public void updateScreening(String movieName,String room, String startTime) {
        ScreeningId screeningId = new ScreeningId(movieName,room, LocalDateTime.parse(startTime,formatter));
        Optional<Screening> screeningOptional = screeningRepository.findById(screeningId);
        if (screeningOptional.isEmpty()) {
            throw new RuntimeException("Screening does not exists");
        }

        screeningRepository.save(screeningOptional.get());
    }

    @Override
    public void deleteScreening(String movieName,String room, String startTime) {
        ScreeningId screeningId = new ScreeningId(movieName,room, LocalDateTime.parse(startTime,formatter));
        screeningRepository.deleteById(screeningId);
    }

    @Override
    public List<Screening> getAllScreenings() {
        return screeningRepository.findAll();
    }
}
