package com.controller;

import com.dto.SectionDto;
import com.dto.SectionResponseDto;
import com.service.SectionService;
import com.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
public class SectionController {

      @Autowired
      private SectionService sectionService;

      @GetMapping("/sections/{sectionId}")
      @ResponseStatus(HttpStatus.OK)
      public SectionResponseDto getSectionById(@PathVariable UUID sectionId){
            log.debug("[X] Request to get section with id={}", sectionId);
            return sectionService.getSectionById(sectionId);
      }

      @PostMapping("/rooms/{roomId}/sections")
      @ResponseStatus(HttpStatus.CREATED)
      public SectionResponseDto saveSection(@PathVariable UUID roomId, @RequestBody @Valid SectionDto section){
            log.debug("[X] Request to create new section in room with id={}", roomId);
            return sectionService.saveSection(roomId, section);
      }

      @PutMapping("sections/{sectionId}")
      @ResponseStatus(HttpStatus.OK)
      public SectionResponseDto updateSection(@PathVariable UUID sectionId, @RequestBody @Valid SectionDto section){
            log.debug("[X] Request to update section with id={}", sectionId);
            return sectionService.updateSection(sectionId, section);
      }

      @DeleteMapping("sections/{sectionId}")
      @ResponseStatus(HttpStatus.OK)
      public Response deleteSection(@PathVariable UUID sectionId){
            log.debug("[X] Request to delete section with id={}", sectionId);
            sectionService.deleteSection(sectionId);
            return new Response("Section has been deleted");
      }
}
