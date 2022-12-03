package com.epam.training.ticketservice.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {

    Optional<Movie> findByName(String name);

    @Query("delete from Movie m where m.name=:name")
    void deleteByName(@Param("name") String name);
}
