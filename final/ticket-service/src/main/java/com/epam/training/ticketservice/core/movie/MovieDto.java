package com.epam.training.ticketservice.core.movie;

import lombok.Value;

@Value
public class MovieDto {
    String name;
    String genre;
    Integer screenTime;
}
