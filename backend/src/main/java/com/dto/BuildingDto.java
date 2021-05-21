package com.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildingDto {
      @NotNull
      private String name;
      @NotNull
      private String address;
      @NotNull
      private int levels;
}
