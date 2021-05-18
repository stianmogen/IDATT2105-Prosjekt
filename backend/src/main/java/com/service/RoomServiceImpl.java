package com.service;

import com.dto.*;
import com.dto.RoomResponseDto;
import com.exception.BuildingNotFoundException;
import com.exception.RoomNotFoundException;
import com.exception.RoomNotFoundException;
import com.model.Building;
import com.model.Room;
import com.model.Room;
import com.querydsl.core.types.Predicate;
import com.repository.BuildingRepository;
import com.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoomServiceImpl implements RoomService{
      ModelMapper modelMapper = new ModelMapper();

      @Autowired
      private RoomRepository roomRepository;

      @Autowired
      private BuildingRepository buildingRepository;

      @Override
      public RoomResponseDto getRoomById(UUID roomId) {
            Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
            return modelMapper.map(room, RoomResponseDto.class);
      }

      @Override
      public Room getRoomObjectById(UUID roomId){
            return roomRepository.findById(roomId).orElseThrow();
      }

      @Override
      public RoomResponseDto updateRoom(UUID roomId, RoomDto roomDto) {
            Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
            room.setName(roomDto.getName());
            room.setCapacity(roomDto.getCapacity());
            room.setLevel(roomDto.getLevel());
            roomRepository.save(room);
            return modelMapper.map(room, RoomResponseDto.class);
      }

      @Override
      public RoomResponseDto saveRoom(UUID buildingId, RoomDto roomDto) {
            Room room = modelMapper.map(roomDto, Room.class);
            Building building = buildingRepository.findById(buildingId).orElseThrow(BuildingNotFoundException::new);
            room.setBuilding(building);
            room = roomRepository.save(room);
            return modelMapper.map(room, RoomResponseDto.class);
      }

      @Override
      public void deleteRoom(UUID roomId) {
            Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
            roomRepository.delete(room);
      }

      @Override
      public Page<SectionDto> getSections(Predicate predicate, Pageable pageable, UUID id) {
            //TODO: Make service and repo method
            return null;
      }
}
