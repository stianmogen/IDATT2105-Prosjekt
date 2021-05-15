package com.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateReservationDto {
      @NotEmpty
      private List<UUID> sectionsIds;
      private String description;
      @NotNull
      private int participants;
      @NotNull
      private ZonedDateTime from;
      @NotNull
      private ZonedDateTime to;
}