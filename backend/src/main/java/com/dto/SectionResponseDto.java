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
public class SectionResponseDto {
      private UUID id;
      private RoomListDto room;
      private String name;
      private int capacity;
}
