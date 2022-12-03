package com.epam.training.ticketservice.core.bookingprice;

import com.epam.training.ticketservice.core.bookingprice.calculator.PriceCalculator;
import com.epam.training.ticketservice.core.screening.Screening;
import com.epam.training.ticketservice.core.seat.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookingPriceServiceImpl implements BookingPriceService {

    private final BookingPriceRepository bookingPriceRepository;
    private final PriceCalculator priceCalculator;

    @Override
    public void updateBasePrice(Long price) {
        BookingPrice basePrice = bookingPriceRepository.findById(1L).get();
        basePrice.setPrice(price);
        bookingPriceRepository.save(basePrice);
    }

    @Override
    public Long getBookingPrice(Screening screening, Set<Seat> seats) {
        return priceCalculator.calculateTotalPrice(seats, screening);
    }
}
