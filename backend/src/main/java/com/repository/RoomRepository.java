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
    
    List<Room> findAvailableRoom(@Param("startTime")ZonedDateTime from,
                                 @Param("endTime")ZonedDateTime to,
                                 @Param("participants")int participants);

}
