package com.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDto {
      @NotNull
      private UUID buildingId;
      @NotNull
      private int level;
      @NotNull
      private String name;
      @NotNull
      private int capacity;
}
