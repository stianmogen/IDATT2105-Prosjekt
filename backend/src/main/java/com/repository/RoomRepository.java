package com.repository;

import com.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID>, QuerydslPredicateExecutor<Room> {


    @Query("SELECT r FROM Room r "+
            "INNER JOIN r.sections s " +
            "WHERE s = (SELECT sec FROM Section sec " +
            "INNER JOIN sec.reservations res " +
            "WHERE res.startTime < :startTime " +
            "AND res.endTime > :endTime " +
            "AND :participants >= 0) ")
            //"AND (SELECT SUM(capacity) from s) > :participants)"
    List<Room> findAvailableRoom(@Param("startTime") ZonedDateTime from,
                                 @Param("endTime") ZonedDateTime to,
                                 @Param("participants") int participants);

}
