package com.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private UUID id;
    @NotNull
    private String firstName;
    @NotNull
    private String surname;
    @NotNull
    private String email;
    private String phone;
}
