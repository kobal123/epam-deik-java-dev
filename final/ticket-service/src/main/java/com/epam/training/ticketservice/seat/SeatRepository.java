package com.epam.training.ticketservice.seat;

import com.epam.training.ticketservice.screening.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Long> {
    List<Seat> findSeatsByScreening(Screening screening);
}
