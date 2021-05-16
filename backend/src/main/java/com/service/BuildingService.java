package com.service;

import com.dto.BuildingDto;
import com.dto.BuildingResponseDto;
import com.dto.RoomDto;
import com.dto.RoomResponseDto;
import com.model.Room;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.UUID;

public interface BuildingService {
      BuildingResponseDto getBuildingById(UUID id);
      BuildingResponseDto updateBuilding(UUID id, BuildingDto building);
      BuildingResponseDto saveBuilding(BuildingDto building);
      void deleteBuilding(UUID id);
      Page<RoomResponseDto> getRoomsInBuildingById(UUID buildingId, Pageable pageable);
}
