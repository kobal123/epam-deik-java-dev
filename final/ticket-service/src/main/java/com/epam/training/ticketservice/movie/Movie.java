package com.epam.training.ticketservice.movie;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "movies")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    private String name;
    private String genre;
    private int screenTime;

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public int getScreenTime() {
        return screenTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Movie movie = (Movie) o;
        return screenTime == movie.screenTime
                && Objects.equals(name, movie.name)
                && Objects.equals(genre, movie.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, genre, screenTime);
    }
}
