package com.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildingDto {
      private UUID id;
      @NotNull
      private String address;
      @NotNull
      private int levels;
}
