package com.epam.training.ticketservice.bookingprice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingPriceRepository extends JpaRepository<BookingPrice, Long> {
}
