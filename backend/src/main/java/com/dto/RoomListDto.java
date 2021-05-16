package com.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomListDto {
      private UUID id;
      private int level;
      private String name;
      private int capacity;
}
