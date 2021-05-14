package com.model;

import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor()
@EqualsAndHashCode(callSuper = true)
@Table(name="reservation")
public class Reservation extends UUIDModel{

    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(
          name = "reservations_sections",
          joinColumns = @JoinColumn(
                name = "reservation_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(
                name = "section_id", referencedColumnName = "id"))
    private List<Section> sections;

    @NotNull
    private ZonedDateTime from;

    @NotNull
    private ZonedDateTime to;

    @NotNull
    private int participants;

    private String description;
}
