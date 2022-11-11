package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.movie.MovieRepository;
import com.epam.training.ticketservice.room.RoomRepository;
import com.epam.training.ticketservice.screening.exception.OverlappingScreeningBreakTimeException;
import com.epam.training.ticketservice.screening.exception.OverlappingScreeningException;
import com.epam.training.ticketservice.screening.exception.ScreeningNotFoundException;
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
    private final MovieRepository movieRepository;


    public ScreeningServiceImpl(DateTimeFormatter formatter,
                                ScreeningRepository screeningRepository,
                                MovieRepository movieRepository) {
        this.formatter = formatter;
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
    }



    @Override
    public void createScreening(Screening screening) {
        Optional<Screening> screeningOptional = screeningRepository.getScreeningByMovieAndRoomAndStartTime(
                screening.getMovieTitle(),
                screening.getRoomName(),
                screening.getStartTime());
        if (screeningOptional.isPresent()) {
            throw new RuntimeException("Screening already exists");
        }
        validateScreening(screening);
        screeningRepository.save(screening);

    }

    public void updateScreening(Screening screening) {
        Optional<Screening> screeningOptional = screeningRepository.findById(screening.getId());
        if (screeningOptional.isEmpty()) {
            throw new RuntimeException("Screening does not exists");
        }

        validateScreening(screening);
        screeningRepository.save(screening);
    }

    @Override
    public Optional<Screening> getScreeningByMovieAndRoomAndStartTime(String movie, String room, LocalDateTime start) {
        return screeningRepository.getScreeningByMovieAndRoomAndStartTime(movie, room, start);
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


        if (isSecondStartBetweenScreeningPeriod || isSecondEndBetweenScreeningPeriod) {
            throw new OverlappingScreeningException();
        } else if (isSecondStartInBreakTime) {
            throw new OverlappingScreeningBreakTimeException();
        }

    }




    @Override
    public void deleteScreening(String movieName,String room, String startTime) {
        //Screening screening = new ScreeningId(movieName,room, LocalDateTime.parse(startTime,formatter));
        Optional<Screening> screeningOptional = screeningRepository.
                getScreeningByMovieAndRoomAndStartTime(movieName,
                        room,
                        LocalDateTime.parse(startTime,formatter));

        if (screeningOptional.isEmpty()) {
            throw new ScreeningNotFoundException("Failed to delete screening, screening does not exists");
        }
        screeningRepository.save(screeningOptional.get());
    }

    @Override
    public Optional<Screening> getScreeningById(Long screeningId) {
        return screeningRepository.findById(screeningId);
    }

    @Override
    public List<Screening> getAllScreenings() {
        return screeningRepository.findAll();
    }
}
