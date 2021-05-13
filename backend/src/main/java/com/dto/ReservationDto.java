package com.dto;

import com.model.ReservationId;
import com.model.Section;
import com.model.UUIDModel;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto{
    @NonNull
    private ReservationId reservationId;
    private Section section;

    private int participants;

    private ZonedDateTime from;
    private ZonedDateTime to;
}
