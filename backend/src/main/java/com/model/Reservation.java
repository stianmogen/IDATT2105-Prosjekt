package com.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
          name = "reservation_section",
          joinColumns = @JoinColumn(
                name = "reservation_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(
                name = "section_id", referencedColumnName = "id"))
    private List<Section> sections;

    @NotNull
    private ZonedDateTime startTime;

    @NotNull
    private ZonedDateTime endTime;

    @NotNull
    private int participants;

    private String description;

}
