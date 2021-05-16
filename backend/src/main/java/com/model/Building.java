package com.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
@Table(name = "building")
@EqualsAndHashCode(callSuper = true)
public class Building extends UUIDModel{
      @NotNull
      private String address;
      @NotNull
      private int levels;
}
