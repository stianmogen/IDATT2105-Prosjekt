package com.service;

import com.dto.RoomResponseDto;
import com.dto.SectionDto;
import com.dto.SectionResponseDto;
import com.exception.RoomNotFoundException;
import com.exception.SectionNotAvailableException;
import com.exception.SectionNotFoundException;
import com.model.Building;
import com.model.Room;
import com.model.Section;
import com.repository.RoomRepository;
import com.repository.SectionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class SectionServiceImpl implements SectionService{
      ModelMapper modelMapper = new ModelMapper();

      @Autowired
      private SectionRepository sectionRepository;

      @Autowired
      private RoomRepository roomRepository;

      @Override
      public SectionResponseDto getSectionById(UUID sectionId) {
            Section section = sectionRepository.findById(sectionId).orElseThrow(SectionNotFoundException::new);
            return modelMapper.map(section, SectionResponseDto.class);
      }

      @Override
      public SectionResponseDto updateSection(UUID sectionId, SectionDto sectionDto) {
            Section section = sectionRepository.findById(sectionId).orElseThrow(SectionNotFoundException::new);
            Room room = roomRepository.findById(sectionDto.getRoomId()).orElseThrow(RoomNotFoundException::new);
            section.setRoom(room);
            section.setName(sectionDto.getName());
            section.setCapacity(sectionDto.getCapacity());
            sectionRepository.save(section);
            return modelMapper.map(section, SectionResponseDto.class);
      }

      @Override
      public SectionResponseDto saveSection(SectionDto sectionDto) {
            Section section = sectionRepository.save(modelMapper.map(sectionDto, Section.class));
            return modelMapper.map(section, SectionResponseDto.class);
      }

      @Override
      public void deleteSection(UUID sectionId) {
            Section section = sectionRepository.findById(sectionId).orElseThrow(SectionNotFoundException::new);
            sectionRepository.delete(section);

      }

      public SectionResponseDto findAvailable(UUID sectionId, ZonedDateTime from, ZonedDateTime to){
            Section section = sectionRepository.findAvailableSection(sectionId, from, to).orElseThrow(SectionNotAvailableException::new);
            return modelMapper.map(section, SectionResponseDto.class);
      }
}
