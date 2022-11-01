package com.epam.training.ticketservice.screening;

import java.util.List;
import java.util.Optional;

public interface ScreeningService {


    void createScreening(Screening screening);


    void deleteScreening(String movieName,String room, String startTime);

    Optional<Screening> getScreeningById(ScreeningId screeningId);

    List<Screening> getAllScreenings();

    void updateScreening(Screening screening);
}
