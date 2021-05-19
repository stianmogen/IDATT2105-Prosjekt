package com.controller;

import com.dto.*;
import com.model.Building;
import com.model.Room;
import com.model.User;
import com.querydsl.core.types.Predicate;
import com.service.BuildingService;
import com.utils.Constants;
import com.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/buildings/")
public class BuildingController {

      @Autowired
      private BuildingService buildingService;

      @GetMapping("{buildingId}")
      @ResponseStatus(HttpStatus.OK)
      public BuildingResponseDto getBuildingById(@PathVariable UUID buildingId){
            log.debug("[X] Request to get building with id={}", buildingId);
            return buildingService.getBuildingById(buildingId);
      }

      @GetMapping
      @ResponseStatus(HttpStatus.OK)
      public Page<BuildingResponseDto> getAllBuildings(@QuerydslPredicate(root = Building.class) Predicate predicate,
                                       @PageableDefault(size = Constants.PAGINATION_SIZE, sort="name", direction = Sort.Direction.ASC)Pageable pageable){
            log.debug("[X] Request to look up all buildings");
            return this.buildingService.getAllBuildingsDto(predicate, pageable);
      }

      @PostMapping
      @ResponseStatus(HttpStatus.CREATED)
      public BuildingResponseDto saveBuilding(@RequestBody @Valid BuildingDto building){
            log.debug("[X] Request to create new building");
            return buildingService.saveBuilding(building);
      }

      @PutMapping("{buildingId}")
      @ResponseStatus(HttpStatus.OK)
      public BuildingResponseDto updateBuilding(@PathVariable UUID buildingId, @RequestBody @Valid BuildingDto building){
            log.debug("[X] Request to update building with id={}", buildingId);
            return buildingService.updateBuilding(buildingId, building);
      }

      @DeleteMapping("{buildingId}")
      @ResponseStatus(HttpStatus.OK)
      public Response deleteBuilding(@PathVariable UUID buildingId){
            log.debug("[X] Request to delete building with id={}", buildingId);
            buildingService.deleteBuilding(buildingId);
            return new Response("Building has been deleted");
      }
}
