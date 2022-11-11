package com.epam.training.ticketservice.movie;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String genre;
    private Integer screenTime;

    public Movie(String name, String genre, Integer screenTime) {
        this.name = name;
        this.genre = genre;
        this.screenTime = screenTime;
    }
}
