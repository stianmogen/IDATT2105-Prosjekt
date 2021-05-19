package com.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Table(name = "room")
@EqualsAndHashCode(callSuper = true)
public class Room extends UUIDModel{
      @ManyToOne(fetch = FetchType.LAZY, optional = false)
      private Building building;
      @OneToMany(mappedBy = "room", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
      private List<Section> sections;
      @NotNull
      private int level;
      @NotNull
      private String name;
}
