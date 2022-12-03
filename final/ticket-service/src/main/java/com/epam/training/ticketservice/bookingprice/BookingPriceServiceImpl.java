package com.epam.training.ticketservice.bookingprice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingPriceServiceImpl implements BookingPriceService{

    private final BookingPriceRepository bookingPriceRepository;

    @Override
    public void updateBasePrice(Long price) {
        BookingPrice basePrice = bookingPriceRepository.findById(1L).get();
        basePrice.setPrice(price);
        bookingPriceRepository.save(basePrice);
    }
}
