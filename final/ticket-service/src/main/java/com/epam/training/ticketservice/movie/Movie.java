package com.epam.training.ticketservice.movie;


import com.epam.training.ticketservice.pricecomponent.PriceComponent;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;


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

    @OneToMany(fetch = FetchType.EAGER)
    private Set<PriceComponent> priceComponents = new HashSet<>();

    public Movie(String name, String genre, Integer screenTime) {
        this.name = name;
        this.genre = genre;
        this.screenTime = screenTime;
    }

    void addPriceComponent(PriceComponent component) {
        priceComponents.add(component);
    }
}
