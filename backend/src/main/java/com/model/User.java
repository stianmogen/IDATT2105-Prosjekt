package com.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;

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
}
