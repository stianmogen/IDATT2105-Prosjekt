package com.controller;

import com.dto.SectionDto;
import com.dto.SectionResponseDto;
import com.model.Reservation;
import com.querydsl.core.types.Predicate;
import com.service.SectionService;
import com.utils.Constants;
import com.utils.Response;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
public class SectionController {

      @Autowired
      private SectionService sectionService;

      @GetMapping("/sections/{sectionId}/")
      @ApiOperation(value = "Gets a specific sections info")
      @ResponseStatus(HttpStatus.OK)
      public SectionResponseDto getSectionById(@PathVariable UUID sectionId){
            log.debug("[X] Request to get section with id={}", sectionId);
            return sectionService.getSectionById(sectionId);
      }

      @PostMapping("/rooms/{roomId}/sections/")
      @ApiOperation(value = "Creates and persists a sections info")
      @ResponseStatus(HttpStatus.CREATED)
      public SectionResponseDto saveSection(@PathVariable UUID roomId, @RequestBody @Valid SectionDto section){
            log.debug("[X] Request to create new section in room with id={}", roomId);
            return sectionService.saveSection(roomId, section);
      }

      @GetMapping("/rooms/{roomId}/sections")
      @ApiOperation(value = "Gets all section for a specific room")
      @ResponseStatus(HttpStatus.OK)
      public Page<SectionResponseDto> getSections(@QuerydslPredicate(root = Reservation.class) Predicate predicate,
                                                         @PageableDefault(size = Constants.PAGINATION_SIZE, sort="capacity", direction = Sort.Direction.ASC) Pageable pageable,
                                                  @PathVariable UUID roomId) {
            log.debug("[X] Request to get sections");
            return sectionService.getSectionsForRoom(predicate, pageable, roomId);
      }

      @PutMapping("sections/{sectionId}/")
      @ApiOperation(value = "Updates a specific sections info")
      @ResponseStatus(HttpStatus.OK)
      public SectionResponseDto updateSection(@PathVariable UUID sectionId, @RequestBody @Valid SectionDto section){
            log.debug("[X] Request to update section with id={}", sectionId);
            return sectionService.updateSection(sectionId, section);
      }

      @DeleteMapping("sections/{sectionId}/")
      @ApiOperation(value = "Deletes a specific sections info")
      @ResponseStatus(HttpStatus.OK)
      public Response deleteSection(@PathVariable UUID sectionId){
            log.debug("[X] Request to delete section with id={}", sectionId);
            sectionService.deleteSection(sectionId);
            return new Response("Section has been deleted");
      }
}
