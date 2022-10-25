package com.epam.training.ticketservice.room;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "room_table")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    private String name;
    private int seatRows;
    private int seatCols;

    public int getCapacity() {
        return seatRows * seatCols;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return seatRows == room.seatRows && seatCols == room.seatCols && Objects.equals(name, room.name);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
