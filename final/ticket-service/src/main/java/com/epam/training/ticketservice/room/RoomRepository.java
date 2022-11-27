package com.epam.training.ticketservice.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
    Optional<Room> findByName(String name);

    Long deleteByName(String name);
}
