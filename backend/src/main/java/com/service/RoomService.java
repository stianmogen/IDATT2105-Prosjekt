package com.service;

import com.dto.BuildingDto;
import com.dto.ReservationDto;
import com.dto.RoomDto;
import com.dto.SectionDto;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RoomService {
      RoomDto getRoomById(UUID id);
      RoomDto updateRoom(UUID id, RoomDto room);
      RoomDto saveRoom(RoomDto room);
      void deleteRoom(UUID id);
      Page<SectionDto> getSections(Predicate predicate, Pageable pageable, UUID id);
}
