package com.repository;

import com.model.Reservation;
import com.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findReservationsByUserId(UUID userId);
    List<Reservation> findReservationsBySectionsContains(Section section);

}