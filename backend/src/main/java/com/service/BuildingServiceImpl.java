package com.service;

import com.dto.*;
import com.exception.BuildingNotFoundException;
import com.model.Building;
import com.repository.BuildingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BuildingServiceImpl implements BuildingService{
      ModelMapper modelMapper = new ModelMapper();

      @Autowired
      private BuildingRepository buildingRepository;

      @Override
      public BuildingResponseDto getBuildingById(UUID buildingId) {
            Building building = buildingRepository.findById(buildingId).orElseThrow(BuildingNotFoundException::new);
            return modelMapper.map(building, BuildingResponseDto.class);
      }

      @Override
      public BuildingResponseDto updateBuilding(UUID buildingId, BuildingDto buildingDto) {
            Building building = buildingRepository.findById(buildingId).orElseThrow(BuildingNotFoundException::new);
            building.setAddress(buildingDto.getAddress());
            building.setName(buildingDto.getName());
            building.setLevels(buildingDto.getLevels());
            buildingRepository.save(building);
            return modelMapper.map(building, BuildingResponseDto.class);
      }

      @Override
      public BuildingResponseDto saveBuilding(BuildingDto buildingDto) {
            Building building = buildingRepository.save(modelMapper.map(buildingDto, Building.class));
            return modelMapper.map(building, BuildingResponseDto.class);
      }

      @Override
      public void deleteBuilding(UUID buildingId) {
            Building building = buildingRepository.findById(buildingId).orElseThrow(BuildingNotFoundException::new);
            buildingRepository.delete(building);
      }

      @Override
      public Page<RoomResponseDto> getRoomsInBuildingById(UUID buildingId, Pageable pageable) {
            //TODO: Make method and repo logic
            return null;
      }
}
