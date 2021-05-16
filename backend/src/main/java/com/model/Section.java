package com.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
@Table(name = "section")
@EqualsAndHashCode(callSuper = true)
public class Section extends UUIDModel{
      @ManyToOne(fetch = FetchType.LAZY, optional = false)
      private Room room;
      @NotNull
      private String name;
      @NotNull
      private int capacity;

      @ManyToMany(mappedBy = "sections")
      private List<Reservation> reservations;
}
