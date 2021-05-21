package com.controller;

import com.dto.BuildingDto;
import com.dto.BuildingResponseDto;
import com.model.Building;
import com.querydsl.core.types.Predicate;
import com.service.BuildingService;
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
@RequestMapping("/buildings/")
public class BuildingController {

      @Autowired
      private BuildingService buildingService;

      @GetMapping("{buildingId}")
      @ApiOperation(value = "Gets a specific buildings info")
      @ResponseStatus(HttpStatus.OK)
      public BuildingResponseDto getBuildingById(@PathVariable UUID buildingId){
            log.debug("[X] Request to get building with id={}", buildingId);
            return buildingService.getBuildingById(buildingId);
      }

      @GetMapping
      @ApiOperation(value = "Gets all buildings info")
      @ResponseStatus(HttpStatus.OK)
      public Page<BuildingResponseDto> getAllBuildings(@QuerydslPredicate(root = Building.class) Predicate predicate,
                                       @PageableDefault(size = Constants.PAGINATION_SIZE, sort="name", direction = Sort.Direction.ASC)Pageable pageable){
            log.debug("[X] Request to look up all buildings");
            return this.buildingService.getAllBuildingsDto(predicate, pageable);
      }

      @PostMapping
      @ApiOperation(value = "Creates and persists a buildings info")
      @ResponseStatus(HttpStatus.CREATED)
      public BuildingResponseDto saveBuilding(@RequestBody @Valid BuildingDto building){
            log.debug("[X] Request to create new building");
            return buildingService.saveBuilding(building);
      }

      @PutMapping("{buildingId}")
      @ApiOperation(value = "Update a specific buildings info")
      @ResponseStatus(HttpStatus.OK)
      public BuildingResponseDto updateBuilding(@PathVariable UUID buildingId, @RequestBody @Valid BuildingDto building){
            log.debug("[X] Request to update building with id={}", buildingId);
            return buildingService.updateBuilding(buildingId, building);
      }

      @DeleteMapping("{buildingId}")
      @ApiOperation(value = "Deletes a specific buildings' info")
      @ResponseStatus(HttpStatus.OK)
      public Response deleteBuilding(@PathVariable UUID buildingId){
            log.debug("[X] Request to delete building with id={}", buildingId);
            buildingService.deleteBuilding(buildingId);
            return new Response("Building has been deleted");
      }
}
