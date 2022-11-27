package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.movie.MovieRepository;
import com.epam.training.ticketservice.screening.exception.OverlappingScreeningBreakTimeException;
import com.epam.training.ticketservice.screening.exception.OverlappingScreeningException;
import com.epam.training.ticketservice.screening.exception.ScreeningNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService {
    private static final int BREAK_TIME = 10;
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final ScreeningConverter screeningConverter;


    @Override
    public void createScreening(ScreeningDto screening) {
        Optional<Screening> screeningOptional = screeningRepository.findScreeningByMovieAndRoomAndStartTime(
                screening.getMovieTitle(),
                screening.getRoomName(),
                screening.getStartTime());

        if (screeningOptional.isPresent()) {
            throw new RuntimeException("Screening already exists");
        }
        validateScreening(screening);
        Screening screeningToSave = screeningFromDto(screening);

        screeningRepository.save(screeningToSave);

    }

    public void updateScreening(ScreeningDto screening) {
        Optional<Screening> screeningOptional = screeningRepository.findScreeningByMovieAndRoomAndStartTime(
                screening.getMovieTitle(),
                screening.getRoomName(),
                screening.getStartTime()
        );

        if (screeningOptional.isEmpty()) {
            throw new RuntimeException("Screening does not exists");
        }

        validateScreening(screening);
        Screening screeningToSave = screeningFromDto(screening);
        screeningRepository.save(screeningToSave);
    }

    @Override
    public Optional<Screening> getScreeningByMovieAndRoomAndStartTime(String movie, String room, LocalDateTime start) {
        return screeningRepository.findScreeningByMovieAndRoomAndStartTime(movie, room, start);
    }


    private void validateScreening(ScreeningDto screeningToSave) {
        List<Screening> screenings = screeningRepository.findAll();
        for (Screening screening : screenings) {

            if (screening.getRoomName().equals(screeningToSave.getRoomName())) {
                checkScreeningDateCollision(screening,screeningToSave);
            }
        }

    }

    private void checkScreeningDateCollision(Screening firstScreening, ScreeningDto secondScreening) {
        Movie firstMovie = movieRepository.findByName(firstScreening.getMovieTitle()).get();
        Movie secondMovie = movieRepository.findByName(secondScreening.getMovieTitle()).get();
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
    public void deleteScreening(ScreeningDto screeningDto) {
        Optional<Screening> screeningOptional = screeningRepository
                .findScreeningByMovieAndRoomAndStartTime(
                        screeningDto.getMovieTitle(),
                        screeningDto.getRoomName(),
                        screeningDto.getStartTime()
                );

        if (screeningOptional.isEmpty()) {
            throw new IllegalArgumentException("Failed to delete screening, screening does not exists");
        }
        screeningRepository.deleteById(screeningOptional.get().getId());
    }

    @Override
    public List<ScreeningDto> getAllScreenings() {
        return screeningRepository.findAll().stream()
                .map(screeningConverter::toDto)
                .collect(Collectors.toList());
    }

    private Screening screeningFromDto(ScreeningDto dto) {
        return new Screening(
                dto.getMovieTitle(),
                dto.getRoomName(),
                dto.getStartTime()
        );
    }

    private void updateScreeningFromDto(Screening screening, ScreeningDto dto) {
        screening.setStartTime(dto.getStartTime());
        screening.setMovieTitle(dto.getMovieTitle());
        screening.setRoomName(dto.getRoomName());
    }
}
