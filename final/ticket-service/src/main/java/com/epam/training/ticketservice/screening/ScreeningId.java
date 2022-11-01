package com.epam.training.ticketservice.screening;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Column;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScreeningId  implements Serializable {

    @Column(name = "movie_title")
    private String movieTitle;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "start_time")
    private LocalDateTime startTime;


}
