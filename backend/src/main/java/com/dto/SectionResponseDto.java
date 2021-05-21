package com.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionResponseDto {
      private UUID id;
      private RoomListDto room;
      private String name;
      private int capacity;
}
