package com.repository;

import com.model.QReservation;
import com.model.Reservation;
import com.model.Section;
import com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID>, QuerydslPredicateExecutor<Reservation> {
    List<Reservation> findReservationsByUserId(UUID userId);
    List<Reservation> findReservationsBySectionsContains(Section section);
    void deleteReservationByUser(User user);
}
