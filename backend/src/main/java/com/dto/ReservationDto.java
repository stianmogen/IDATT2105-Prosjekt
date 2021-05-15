package com.dto;

import com.model.Section;
import com.model.User;
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
public class ReservationDto{
    private UUID id;
    @NotNull
    private UserDto user;
    @NotEmpty
    private List<SectionDto> sections;
    private String description;
    @NotNull
    private int participants;
    @NotNull
    private ZonedDateTime from;
    @NotNull
    private ZonedDateTime to;
}