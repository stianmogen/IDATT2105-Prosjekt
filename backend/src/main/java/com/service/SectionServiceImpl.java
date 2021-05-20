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
import com.querydsl.core.types.Predicate;
import com.repository.RoomRepository;
import com.repository.SectionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SectionServiceImpl implements SectionService{
      ModelMapper modelMapper = new ModelMapper();

      @Autowired
      private SectionRepository sectionRepository;

      @Autowired
      private RoomService roomService;

      @Autowired
      private RoomRepository roomRepository;

      @Override
      public SectionResponseDto getSectionById(UUID sectionId) {
            Section section = sectionRepository.findById(sectionId).orElseThrow(SectionNotFoundException::new);
            return modelMapper.map(section, SectionResponseDto.class);
      }

      @Override
      public List<Section> getSectionByRoomId(UUID roomId){
            List<Section> sections = sectionRepository.findAllByRoomId(roomId);
            return sections;
      }

      @Override
      public Page<SectionResponseDto> getSections(Predicate predicate, Pageable pageable) {
            return sectionRepository.findAll(predicate, pageable).map(s -> modelMapper.map(s, SectionResponseDto.class));
      }


      @Override
      public SectionResponseDto updateSection(UUID sectionId, SectionDto sectionDto) {
            Section section = sectionRepository.findById(sectionId).orElseThrow(SectionNotFoundException::new);
            section.setName(sectionDto.getName());
            section.setCapacity(sectionDto.getCapacity());
            sectionRepository.save(section);
            return modelMapper.map(section, SectionResponseDto.class);
      }

      @Override
      public SectionResponseDto saveSection(UUID roomId, SectionDto sectionDto) {
            Section section = modelMapper.map(sectionDto, Section.class);
            Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
            //TODO add check for total capacity
            section.setRoom(room);
            section = sectionRepository.save(section);
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
