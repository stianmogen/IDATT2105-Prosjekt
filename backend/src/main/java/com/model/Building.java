package com.model;

import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

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
