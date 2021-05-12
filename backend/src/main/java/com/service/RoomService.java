package com.service;

import com.dto.BuildingDto;
import com.dto.RoomDto;
import com.dto.SectionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RoomService {
      RoomDto getRoomById(UUID id);
      BuildingDto updateRoom(UUID id, BuildingDto building);
      BuildingDto saveRoom(BuildingDto activity);
      void deleteRoom(UUID id);
      Page<SectionDto> getSections(Predicate predicate, Pageable pageable, UUID id);
}
