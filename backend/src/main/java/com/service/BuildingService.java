package com.service;

import com.dto.BuildingDto;
import com.dto.RoomDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.UUID;

public interface BuildingService {
      BuildingDto getBuildingById(UUID id);
      BuildingDto updateBuilding(UUID id, BuildingDto building);
      BuildingDto saveBuilding(BuildingDto activity);
      void deleteBuilding(UUID id);
      Page<RoomDto> getRooms(Predicate predicate, Pageable pageable, UUID id);
}
