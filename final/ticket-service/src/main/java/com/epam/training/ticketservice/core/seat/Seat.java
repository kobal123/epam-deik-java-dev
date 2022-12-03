package com.epam.training.ticketservice.core.seat;

import com.epam.training.ticketservice.core.screening.Screening;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"screening_id",
        "seatRow",
        "seatCol"})})
public class Seat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer seatRow;
    private Integer seatCol;



    @OneToOne
    @JoinColumn(name = "screening_id", referencedColumnName = "id")
    private Screening screening;

    public Seat(Integer seatRow, Integer seatCol) {
        this.seatRow = seatRow;
        this.seatCol = seatCol;
    }

}
