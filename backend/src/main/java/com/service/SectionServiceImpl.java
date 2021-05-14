package com.service;

import com.dto.SectionDto;
import com.exception.SectionNotAvailableException;
import com.model.Section;
import com.repository.SectionRepository;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class SectionServiceImpl implements SectionService{

      SectionRepository sectionRepository;

      @Override
      public SectionDto getSectionById(UUID id) {
            return null;
      }

      @Override
      public SectionDto updateSection(UUID id, SectionDto section) {
            return null;
      }

      @Override
      public SectionDto saveSection(SectionDto section) {
            return null;
      }

      @Override
      public void deleteSection(UUID id) {

      }

      public Section findAvailable(UUID section, ZonedDateTime from, ZonedDateTime to){
            return sectionRepository.findAvailableSection(section, from, to).orElseThrow(SectionNotAvailableException::new);
      }
}
