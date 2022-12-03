package com.epam.training.ticketservice.core.seat;

import com.epam.training.ticketservice.core.screening.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Long> {
    List<Seat> findSeatsByScreening(Screening screening);
}
