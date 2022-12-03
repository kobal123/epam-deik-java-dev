package com.epam.training.ticketservice.core.bookingprice;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class BookingPrice {
    @Id
    private Long id = 1L;

    private Long price = 1500L;
}
