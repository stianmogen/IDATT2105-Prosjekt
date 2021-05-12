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
@Table(name = "section")
@EqualsAndHashCode(callSuper = true)
public class Section extends UUIDModel{
      @ManyToOne
      private Room room;
      @NotNull
      private String name;
      @NotNull
      private int capacity;
}