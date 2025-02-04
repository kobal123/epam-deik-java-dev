package com.epam.training.ticketservice.core.room;


import com.epam.training.ticketservice.core.pricecomponent.PriceComponent;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
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
@Table(name = "room_table")
@Data
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Integer seatRows;
    private Integer seatCols;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<PriceComponent> priceComponents = new HashSet<>();

    public Room(String name, int seatRows, int seatCols) {
        this.name = name;
        this.seatRows = seatRows;
        this.seatCols = seatCols;
    }

    void addPriceComponent(PriceComponent component) {
        priceComponents.add(component);
    }
}
