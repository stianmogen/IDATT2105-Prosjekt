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
@Table(name = "permission")
@EqualsAndHashCode(callSuper = true)
public class Permission extends UUIDModel{
      private String name;

      @ManyToMany(mappedBy = "permissions")
      private Collection<Role> roles;
}
