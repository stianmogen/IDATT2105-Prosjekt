package com.service;

import com.dto.SectionDto;
import com.dto.SectionResponseDto;
import com.model.Room;
import com.model.Section;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface SectionService {
      SectionResponseDto getSectionById(UUID sectionId);


      List<Section> getSectionByRoomId(UUID roomid);
      Page<SectionResponseDto> getSectionsForRoom(Predicate predicate, Pageable pageable, UUID roomId);
      Page<SectionResponseDto> getSectionsForRoomAreFree(Predicate predicate, Pageable pageable);
      SectionResponseDto updateSection(UUID sectionId, SectionDto sectionDto);
      SectionResponseDto saveSection(UUID roomId, SectionDto sectionDto);
      void deleteSection(UUID sectionId);
}
