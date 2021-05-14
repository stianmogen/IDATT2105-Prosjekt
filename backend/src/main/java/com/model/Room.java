package com.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
@Table(name = "room")
@EqualsAndHashCode(callSuper = true)
public class Room extends UUIDModel{
      @ManyToOne
      private Building building;
      @NotNull
      private int level;
      @NotNull
      private String name;
      @NotNull
      private int capacity;
}
