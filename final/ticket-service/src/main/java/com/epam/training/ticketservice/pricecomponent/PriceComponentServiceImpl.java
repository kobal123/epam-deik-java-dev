package com.epam.training.ticketservice.pricecomponent;

import com.epam.training.ticketservice.movie.MovieDto;
import com.epam.training.ticketservice.movie.MovieRepository;
import com.epam.training.ticketservice.movie.MovieService;
import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.room.RoomDto;
import com.epam.training.ticketservice.room.RoomRepository;
import com.epam.training.ticketservice.room.RoomService;
import com.epam.training.ticketservice.screening.ScreeningDto;
import com.epam.training.ticketservice.screening.ScreeningRepository;
import com.epam.training.ticketservice.screening.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriceComponentServiceImpl implements PriceComponentService {

    private final PriceComponentRepository priceComponentRepository;


    @Override
    public void createPriceComponent(String name, Integer amount) {
        PriceComponent component = new PriceComponent(name, amount);
        priceComponentRepository.save(component);
    }
}
