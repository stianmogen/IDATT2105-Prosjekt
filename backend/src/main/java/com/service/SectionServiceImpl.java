package com.service;

import com.dto.SectionDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SectionServiceImpl implements SectionService{
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
}
