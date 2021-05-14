package com.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionDto {
      private UUID id;
      @NotNull
      private String name;
      @NotNull
      private int capacity;
}

