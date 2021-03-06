package com.service;

import com.dto.RoomDto;
import com.dto.RoomResponseDto;
import com.dto.SectionDto;
import com.model.Room;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface RoomService {
      RoomResponseDto getRoomById(UUID roomId);
      RoomResponseDto updateRoom(UUID roomId, RoomDto roomDto);
      RoomResponseDto saveRoom(UUID buildingId, RoomDto roomDto);
      void deleteRoom(UUID roomId);
      Page<SectionDto> getSections(Predicate predicate, Pageable pageable, UUID roomId);
      Room getRoomObjectById(UUID roomId);
      Page<RoomResponseDto> findAllRooms(Predicate predicate, Pageable pageable);
      List<RoomResponseDto> findAvailableRoomsByParticipantsAndDateAndBuilding(Predicate predicate, Pageable pageable, ZonedDateTime startTime, ZonedDateTime endTime, int participants, UUID buildingId);
}
