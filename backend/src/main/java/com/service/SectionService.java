package com.service;

import com.dto.BuildingDto;
import com.dto.RoomDto;
import com.dto.SectionDto;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SectionService {
      SectionDto getSectionById(UUID id);
      SectionDto updateSection(UUID id, SectionDto section);
      SectionDto saveSection(SectionDto section);
      void deleteSection(UUID id);
}
