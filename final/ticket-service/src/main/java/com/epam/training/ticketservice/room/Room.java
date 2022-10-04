package com.epam.training.ticketservice.room;

public class Room {
    private final String name;
    private final int row;
    private final int col;

    public Room(String name, int row, int col) {
        this.name = name;
        this.row = row;
        this.col = col;
    }

    public String getName() {
        return name;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getCapacity(){
        return getRow() * getCol();
    }
}
