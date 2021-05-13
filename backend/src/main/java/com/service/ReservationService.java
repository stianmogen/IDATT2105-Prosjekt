package com.service;

import com.dto.ReservationDto;
import com.model.ReservationId;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface ReservationService {
    ReservationDto saveReservation(UUID userId, UUID sectionId, ZonedDateTime from, ZonedDateTime to, int participants);
    Page<ReservationDto> getReservationsForRoom(Predicate predicate, Pageable pageable, UUID roomId);
    Page<ReservationDto> getReservationsForUser(Predicate predicate, Pageable pageable, UUID userId);
    ReservationDto removeReservation(ReservationId reservationId);
}
