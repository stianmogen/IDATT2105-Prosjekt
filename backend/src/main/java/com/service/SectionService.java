package com.service;

import com.dto.SectionDto;
import com.dto.SectionResponseDto;
import com.model.Room;
import com.model.Section;

import java.util.List;
import java.util.UUID;

public interface SectionService {
      SectionResponseDto getSectionById(UUID sectionId);
      List<Section> getSectionByRoom(Room room);
      SectionResponseDto updateSection(UUID sectionId, SectionDto sectionDto);
      SectionResponseDto saveSection(SectionDto sectionDto);
      void deleteSection(UUID sectionId);
}
