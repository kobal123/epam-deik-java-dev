package com.epam.training.ticketservice.core.bookingprice.calculator;

import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.pricecomponent.PriceComponent;
import com.epam.training.ticketservice.core.room.Room;
import com.epam.training.ticketservice.core.screening.Screening;
import com.epam.training.ticketservice.core.seat.Seat;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class PriceCalculatorImpl implements PriceCalculator {

    @Override
    public Long calculateTotalPrice(Set<Seat> seats, Screening screening) {
        Movie movie = screening.getMovie();
        Room room = screening.getRoom();
        Long moviePriceTotal = movie.getPriceComponents().stream().mapToLong(PriceComponent::getPrice).sum();
        Long roomPriceTotal = room.getPriceComponents().stream().mapToLong(PriceComponent::getPrice).sum();
        Long screeningPriceTotal = screening.getPriceComponents().stream().mapToLong(PriceComponent::getPrice).sum();
        Long ticketPrice = screening.getTicketPrice();

        return seats.size() * (moviePriceTotal + roomPriceTotal + screeningPriceTotal + ticketPrice);
    }
}
