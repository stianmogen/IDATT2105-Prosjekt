package com.service;

import com.dto.SectionDto;

import java.util.UUID;

public interface SectionService {
      SectionDto getSectionById(UUID id);
      SectionDto updateSection(UUID id, SectionDto section);
      SectionDto saveSection(SectionDto section);
      void deleteSection(UUID id);
}
