package com.epam.training.ticketservice.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,BookingId> {
    @Query("SELECT DISTINCT b from Booking b where b.username=:appuser_name")
    List<Booking> getBookingByUsername(@Param("appuser_name") String username);
}
