package com.epam.training.ticketservice.core.booking;

import com.epam.training.ticketservice.core.screening.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    //@Query("SELECT DISTINCT b from Booking b where b.user.name=:appuser_name")
    List<Booking> findBookingByUserName(String username);


    List<Booking> findBookingsByScreening(Screening screening);
}
