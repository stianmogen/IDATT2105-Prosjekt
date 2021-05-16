package com.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponseDto {
      private UUID id;
      private BuildingListDto building;
      private List<SectionListDto> sections;
      private int level;
      private String name;
      private int capacity;
}
