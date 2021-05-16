package com.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildingResponseDto {
      private UUID id;
      private List<RoomListDto> rooms;
      private String name;
      private String address;
      private int levels;
}
