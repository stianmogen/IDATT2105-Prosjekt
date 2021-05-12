package com.service;

import com.dto.RoomDto;
import com.dto.SectionDto;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoomServiceImpl implements RoomService{
      @Override
      public RoomDto getRoomById(UUID id) {
            return null;
      }

      @Override
      public RoomDto updateRoom(UUID id, RoomDto room) {
            return null;
      }

      @Override
      public RoomDto saveRoom(RoomDto room) {
            return null;
      }

      @Override
      public void deleteRoom(UUID id) {

      }

      @Override
      public Page<SectionDto> getSections(Predicate predicate, Pageable pageable, UUID id) {
            return null;
      }
}
