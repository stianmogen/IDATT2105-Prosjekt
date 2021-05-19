package com.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDto {
      @NotNull
      private int level;
      @NotNull
      private String name;
}
