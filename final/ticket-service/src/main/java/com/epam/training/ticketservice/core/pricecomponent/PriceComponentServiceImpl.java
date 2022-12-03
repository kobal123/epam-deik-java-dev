package com.epam.training.ticketservice.core.pricecomponent;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
