package com.repository;

import com.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SectionRepository extends JpaRepository<Section, UUID> {
      List<Section> findAllByRoomId(UUID roomId);

      @Query("SELECT s FROM Section s " +
            "INNER JOIN s.reservations r " +
            "WHERE r.from < :from " +
            "AND r.to > :to " +
            "AND s.id = :id")
      Optional<Section> findAvailableSection(@Param("id") UUID uuid, @Param("from") ZonedDateTime from, @Param("to") ZonedDateTime to);
}
