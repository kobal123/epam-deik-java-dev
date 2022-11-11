package com.epam.training.ticketservice.screening;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScreeningService {


    void createScreening(Screening screening);


    void deleteScreening(String movieName,String room, String startTime);

    Optional<Screening> getScreeningById(Long screeningId);

    List<Screening> getAllScreenings();

    void updateScreening(Screening screening);

    Optional<Screening> getScreeningByMovieAndRoomAndStartTime(String movie, String room, LocalDateTime start);

}
