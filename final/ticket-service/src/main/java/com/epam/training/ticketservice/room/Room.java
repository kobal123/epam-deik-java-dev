package com.epam.training.ticketservice.room;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Objects;

@Entity
@Table(name = "room_table")
public class Room {

    @Id
    private String name;
    private int seatRows;
    private int seatCols;

    public Room(String name, int seatRows, int seatCols) {
        this.name = name;
        this.seatRows = seatRows;
        this.seatCols = seatCols;
    }

    public Room() {

    }

    public String getName() {
        return name;
    }

    public int getSeatRows() {
        return seatRows;
    }

    public int getSeatCols() {
        return seatCols;
    }

    public int getCapacity() {
        return seatRows*seatCols;
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
        return Objects.hash(name, seatRows, seatCols);
    }
}
