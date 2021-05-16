package com.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildingListDto {
      private UUID id;
      private String name;
      private String address;
      private int levels;
}
