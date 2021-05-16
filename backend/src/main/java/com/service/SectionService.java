package com.service;

import com.dto.SectionDto;
import com.dto.SectionResponseDto;

import java.util.UUID;

public interface SectionService {
      SectionResponseDto getSectionById(UUID sectionId);
      SectionResponseDto updateSection(UUID sectionId, SectionDto sectionDto);
      SectionResponseDto saveSection(UUID roomId, SectionDto sectionDto);
      void deleteSection(UUID sectionId);
}
