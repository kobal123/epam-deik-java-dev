package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.screening.Screening;
import lombok.AllArgsConstructor;
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
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"screening_movie_title",
        "screening_room_name",
        "screening_start_time",
        "seatRow",
        "seatCol"})})
public class Seat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer seatRow;
    private Integer seatCol;



    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "screening_movie_title", referencedColumnName = "movie_title"),
            @JoinColumn(name = "screening_room_name", referencedColumnName = "room_name"),
            @JoinColumn(name = "screening_start_time", referencedColumnName = "start_time")
    })

    private Screening screening;


    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", seatRow=" + seatRow +
                ", seatCol=" + seatCol +
                ", screening=" + screening.getScreeningId() +
                '}';
    }
}
