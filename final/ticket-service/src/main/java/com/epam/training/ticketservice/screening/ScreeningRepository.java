package com.epam.training.ticketservice.screening;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening,Long> {

    @Query("SELECT s FROM Screening s "
            + "WHERE s.room.name=:roomName and s.movie.name=:movieName and s.startTime=:startTime")
    Optional<Screening> findScreeningByMovieAndRoomAndStartTime(@Param("movieName") String movie,
                                                                @Param("roomName") String room,
                                                                @Param("startTime") LocalDateTime start);

    List<Screening> findScreeningsByRoomNameAndStartTimeBetween(String roomName,
                                                                LocalDateTime start,
                                                                LocalDateTime end);
}
