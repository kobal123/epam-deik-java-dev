package com.epam.training.ticketservice.screening;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScreeningService {


    void createScreening(ScreeningDto screening);


    void deleteScreening(ScreeningDto screeningDto);


    List<ScreeningDto> getAllScreenings();

    void updateScreening(ScreeningDto screening);

    Optional<Screening> getScreeningByMovieAndRoomAndStartTime(String movie, String room, LocalDateTime start);

}
