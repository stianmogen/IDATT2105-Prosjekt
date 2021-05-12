package com.dto;

import com.sun.istack.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDto {

      @NotNull
      private UUID id;
      @NotNull
      private int level;
      @NotNull
      private String name;
      @NotNull
      private int capacity;
}
