package com.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
