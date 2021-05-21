package com.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRoleDto {
      @NotNull
      private String email;
      @NotNull
      private String role;
}
