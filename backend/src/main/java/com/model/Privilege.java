package com.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
@Table(name = "privilege")
@EqualsAndHashCode(callSuper = true)
public class Privilege extends UUIDModel{
      private String name;

      @ManyToMany(mappedBy = "privileges")
      private Collection<Role> roles;
}
