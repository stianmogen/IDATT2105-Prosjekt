package com.model;

import com.querydsl.core.annotations.PropertyType;
import com.querydsl.core.annotations.QueryType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Table(name = "room")
@EqualsAndHashCode(callSuper = true)
public class Room extends UUIDModel{
      @NotNull
      @ManyToOne(fetch = FetchType.LAZY, optional = false)
      private Building building;
      @OneToMany(mappedBy = "room", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
      private List<Section> sections;
      @NotNull
      private int level;
      @NotNull
      private String name;

      @Transient
      @QueryType(PropertyType.NUMERIC)
      private String capacity;

      @Transient
      @QueryType(PropertyType.STRING)
      private String search;

      @Transient
      @QueryType(PropertyType.DATETIME)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
      private ZonedDateTime  availableAfter;


      @Transient
      @QueryType(PropertyType.DATETIME)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
      private ZonedDateTime availableBefore;
}
