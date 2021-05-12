package com.service;

import com.dto.BuildingDto;
import com.dto.RoomDto;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BuildingServiceImpl implements BuildingService{
      @Override
      public BuildingDto getBuildingById(UUID id) {
            return null;
      }

      @Override
      public BuildingDto updateBuilding(UUID id, BuildingDto building) {
            return null;
      }

      @Override
      public BuildingDto saveBuilding(BuildingDto activity) {
            return null;
      }

      @Override
      public void deleteBuilding(UUID id) {

      }

      @Override
      public Page<RoomDto> getRooms(Predicate predicate, Pageable pageable, UUID id) {
            return null;
      }
}
