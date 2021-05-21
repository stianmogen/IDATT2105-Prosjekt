package com.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionListDto {
      private UUID id;
      private String name;
      private int capacity;
}
