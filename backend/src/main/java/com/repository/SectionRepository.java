package com.repository;

import com.model.Room;
import com.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SectionRepository extends JpaRepository<Section, UUID>, QuerydslPredicateExecutor<Section> {
      List<Section> findAllByRoomId(UUID roomId);
      List<Section> findSectionsByRoom(Room room);

      @Query("SELECT DISTINCT s FROM Section s " +
            "LEFT JOIN s.reservations r " +
            "WHERE((" +
            "r.startTime <= :startTime AND r.endTime <= :startTime " +
            "OR r.startTime >= :endTime AND r.endTime >= :endTime " +
            "OR r.id IS NULL) " +
            "AND s.id = :id)")
      Optional<Section> findAvailableSection(@Param("id") UUID uuid, @Param("startTime") ZonedDateTime from, @Param("endTime") ZonedDateTime to);
}
