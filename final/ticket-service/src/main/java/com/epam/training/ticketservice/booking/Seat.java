package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.screening.Screening;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumns;
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

    @Override
    public String toString() {
        return "Seat{"
                + "id=" + id
                + ", seatRow=" + seatRow
                + ", seatCol=" + seatCol
                + '}';
    }

}
