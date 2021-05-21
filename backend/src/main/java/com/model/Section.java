package com.model;

import com.querydsl.core.annotations.PropertyType;
import com.querydsl.core.annotations.QueryType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

      @Transient
      @QueryType(PropertyType.NUMERIC)
      private int roomCapacity;

      @Transient
      @QueryType(PropertyType.DATETIME)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
      private ZonedDateTime time;
}
