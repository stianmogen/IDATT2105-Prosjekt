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

      private String name;
      @ManyToMany(mappedBy = "roles")
      private Collection<User> users;

      @ManyToMany
      @JoinTable(
            name = "roles_permissions",
            joinColumns = @JoinColumn(
                  name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                  name = "permission_id", referencedColumnName = "id"))
      private Collection<Permission> permissions;
}
