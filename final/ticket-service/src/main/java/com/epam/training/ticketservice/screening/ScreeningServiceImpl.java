package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.bookingprice.BookingPrice;
import com.epam.training.ticketservice.bookingprice.BookingPriceRepository;
import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.movie.MovieRepository;
import com.epam.training.ticketservice.pricecomponent.PriceComponent;
import com.epam.training.ticketservice.pricecomponent.PriceComponentRepository;
import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.room.RoomRepository;
import com.epam.training.ticketservice.screening.exception.OverlappingScreeningBreakTimeException;
import com.epam.training.ticketservice.screening.exception.OverlappingScreeningException;
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
    private final PriceComponentRepository priceComponentRepository;
    private final RoomRepository roomRepository;
    private final BookingPriceRepository bookingPriceRepository;

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
        BookingPrice bookingPrice = bookingPriceRepository.findById(1L).get();
        screeningToSave.setBookingPrice(bookingPrice);
        screeningRepository.save(screeningToSave);

    }



    @Override
    public Optional<ScreeningDto> getScreeningByMovieAndRoomAndStartTime(String movie,
                                                                         String room,
                                                                         LocalDateTime start) {
        Optional<Screening> screening = screeningRepository
                .findScreeningByMovieAndRoomAndStartTime(movie, room, start);
        return screening.map(screeningConverter::toDto);
    }

    @Override
    public void attachPriceComponent(String componentName, ScreeningDto screeningDto) {
        PriceComponent component = priceComponentRepository.findById(componentName)
                .orElseThrow(() -> new IllegalArgumentException("Could not find price component"));

        Screening screening = screeningRepository.findScreeningByMovieAndRoomAndStartTime(
                screeningDto.getMovieTitle(),
                screeningDto.getRoomName(),
                screeningDto.getStartTime()
        ).orElseThrow(() -> new IllegalArgumentException("Could not find screening"));

        screening.addPriceComponent(component);
        screeningRepository.save(screening);
    }


    private void validateScreening(ScreeningDto screeningToSave) {
        List<Screening> screenings = screeningRepository.findAll();
        for (Screening screening : screenings) {

            if (screening.getRoom().getName().equals(screeningToSave.getRoomName())) {
                checkScreeningDateCollision(screening,screeningToSave);
            }
        }

    }

    private void checkScreeningDateCollision(Screening firstScreening, ScreeningDto secondScreening) {
        Movie firstMovie = firstScreening.getMovie();
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

        screeningOptional.ifPresent(screening -> screeningRepository.deleteById(screening.getId()));
    }

    @Override
    public List<ScreeningDto> getAllScreenings() {
        return screeningRepository.findAll().stream()
                .map(screeningConverter::toDto)
                .collect(Collectors.toList());
    }

    private Screening screeningFromDto(ScreeningDto dto) {
        Movie movie = movieRepository.findByName(dto.getMovieTitle())
                .orElseThrow(() -> new IllegalArgumentException("No such movie"));
        Room room = roomRepository.findByName(dto.getRoomName())
                .orElseThrow(() -> new IllegalArgumentException("no such room"));
        return new Screening(
                movie,
                room,
                dto.getStartTime()
        );
    }

}
