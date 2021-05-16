package com.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
@Table(name = "building")
@EqualsAndHashCode(callSuper = true)
public class Building extends UUIDModel{
      @NotNull
      private String name;
      @NotNull
      private String address;
      @NotNull
      private int levels;
      @OneToMany(mappedBy = "building", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
      private List<Room> rooms;
}
