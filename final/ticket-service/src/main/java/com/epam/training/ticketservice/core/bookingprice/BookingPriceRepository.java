package com.epam.training.ticketservice.core.bookingprice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingPriceRepository extends JpaRepository<BookingPrice, Long> {
}
