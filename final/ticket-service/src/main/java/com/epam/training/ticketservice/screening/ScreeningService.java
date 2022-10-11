package com.epam.training.ticketservice.screening;

public interface ScreeningService {

    void createScreening(String movieName,String room, String startTime);

    void updateScreening(String movieName,String room, String startTime);

    void deleteScreening(String movieName,String room, String startTime);

}
