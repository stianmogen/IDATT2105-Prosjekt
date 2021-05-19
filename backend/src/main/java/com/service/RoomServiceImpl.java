package com.service;

import com.dto.RoomDto;
import com.dto.RoomResponseDto;
import com.dto.SectionDto;
import com.exception.BuildingNotFoundException;
import com.exception.RoomNotFoundException;
import com.model.Building;
import com.model.Room;
import com.querydsl.core.types.Predicate;
import com.repository.BuildingRepository;
import com.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
      public Page<RoomResponseDto> findAllRooms(Predicate predicate, Pageable pageable) {
            Page<Room> allRooms = roomRepository.findAll(predicate, pageable);
            return allRooms.map(s -> modelMapper.map(s, RoomResponseDto.class));
      }

      @Override
      public List<RoomResponseDto> findAvailableRoomsByParticipantsAndDateAndBuilding(Predicate predicate, Pageable pageable, ZonedDateTime startTime, ZonedDateTime endTime, int participants, UUID buildingId) {
            List<Room> availableRooms = roomRepository.findAvailableRoom(startTime, endTime, participants);
            return availableRooms.stream().map(s -> modelMapper.map(s, RoomResponseDto.class))
                    .collect(Collectors.toList());
      }

      @Override
      public RoomResponseDto updateRoom(UUID roomId, RoomDto roomDto) {
            Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
            room.setName(roomDto.getName());
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
