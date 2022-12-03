package com.epam.training.ticketservice.screening;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScreeningService {


    void createScreening(ScreeningDto screening);


    void deleteScreening(ScreeningDto screeningDto);


    List<ScreeningDto> getAllScreenings();

    Optional<ScreeningDto> getScreeningByMovieAndRoomAndStartTime(String movie, String room, LocalDateTime start);

    void attachPriceComponent(String componentName, ScreeningDto screening);

}
