package com.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
@Table(name = "role")
@EqualsAndHashCode(callSuper = true)
public class Role extends UUIDModel{
      @Enumerated(EnumType.STRING)
      private ERole name;

      @ManyToMany(mappedBy = "roles")
      private Collection<User> users;
}
