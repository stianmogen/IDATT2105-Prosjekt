package com.repository;

import com.dto.ReservationDto;
import com.model.Reservation;
import com.model.ReservationId;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findReservationsByUserId(UUID userId);
    List<Reservation> findReservationsBySectionId(UUID sectionId);
}
