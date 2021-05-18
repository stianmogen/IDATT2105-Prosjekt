package com.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionDto {
      @NotNull
      private String name;
      @NotNull
      private int capacity;
}

