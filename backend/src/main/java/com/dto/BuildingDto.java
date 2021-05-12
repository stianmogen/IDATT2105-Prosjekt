package com.dto;

import com.sun.istack.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildingDto {

      @NotNull
      private UUID id;
      @NotNull
      private String address;
      @NotNull
      private int levels;
}
