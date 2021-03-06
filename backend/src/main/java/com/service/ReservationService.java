package com.service;

import com.dto.CreateReservationDto;
import com.dto.ReservationDto;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ReservationService {
    ReservationDto saveReservation(CreateReservationDto reservation);
    List<ReservationDto> getReservationsForRoom(Predicate predicate, Pageable pageable, UUID roomId);
    Page<ReservationDto> getReservationsForUser(Predicate predicate, Pageable pageable, String email);
    ReservationDto getReservationById(UUID reservationId);
    void removeReservation(UUID reservationId);
}
