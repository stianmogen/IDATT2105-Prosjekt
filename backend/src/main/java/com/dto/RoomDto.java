package com.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDto {
      private UUID id;
      private int level;
      @NotNull
      private String name;
      @NotNull
      private int capacity;
}
