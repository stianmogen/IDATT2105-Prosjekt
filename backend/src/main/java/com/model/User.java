package com.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(of={"email"}, callSuper = true)
public class User extends UUIDModel{

    private String firstName;
    private String surname;

    @Column(unique = true)
    private String email;
    private String phone;
    private String password;

    @ManyToMany
    @JoinTable(
          name = "users_roles",
          joinColumns = @JoinColumn(
                name = "user_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(
                name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;
}
