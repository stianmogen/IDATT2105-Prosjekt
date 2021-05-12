package com.dto;

import com.sun.istack.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    @NotNull
    private UUID id;
    @NotNull
    private String firstName;
    @NotNull
    private String surname;
    @NotNull
    private String email;
}
