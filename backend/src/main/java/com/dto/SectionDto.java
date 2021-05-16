package com.dto;

import com.model.Room;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionDto {
      @NotNull
      private UUID roomId;
      @NotNull
      private String name;
      @NotNull
      private int capacity;
}

