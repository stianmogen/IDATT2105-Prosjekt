package com.repository;

import com.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {

    @Query("SELECT r FROM Room r " +
            "INNER JOIN r.sections s " +
            "INNER JOIN s.reservations res " +
            "WHERE res.startTime < :starTime " +
            "AND res.endTime > :endTime " +
            "AND (SELECT SUM(capacity) from s) > :participants" )
    List<Room> findAvailableRoom(@Param("startTime")ZonedDateTime from,
                                 @Param("endTime")ZonedDateTime to,
                                 @Param("participants")int participants);

}
