package com.service;

import com.dto.CreateReservationDto;
import com.dto.ReservationDto;
import com.model.ReservationId;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface ReservationService {
    ReservationDto saveReservation(CreateReservationDto reservation, String email);
    List<ReservationDto> getReservationsForRoom(Predicate predicate, Pageable pageable, UUID roomId);
    List<ReservationDto> getReservationsForUser(Predicate predicate, Pageable pageable, String email);
    ReservationDto removeReservation(ReservationId reservationId);
}
