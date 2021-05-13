package com.model;

import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor()
@EqualsAndHashCode
@Table(name="reservation")
public class Reservation {

    @EmbeddedId
    private ReservationId reservationId;

    @MapsId("user_id")
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @MapsId("section_id")
    @ManyToOne
    @JoinColumn(name="section_id", referencedColumnName = "id")
    private Section section;

    private int participants;

    @NotNull
    private ZonedDateTime from;
    @NotNull
    private ZonedDateTime to;
}
