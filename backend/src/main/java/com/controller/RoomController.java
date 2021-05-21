package com.controller;

import com.dto.RoomDto;
import com.dto.RoomResponseDto;
import com.model.Room;
import com.querydsl.core.types.Predicate;
import com.service.RoomService;
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
public class RoomController {

      @Autowired
      private RoomService roomService;

      @GetMapping("/rooms/{roomId}/")
      @ApiOperation(value = "Gets a spesific rooms info")
      @ResponseStatus(HttpStatus.OK)
      public RoomResponseDto getRoomById(@PathVariable UUID roomId){
            log.debug("[X] Request to get room with id={}", roomId);
            return roomService.getRoomById(roomId);
      }

      @GetMapping("/rooms/")
      @ApiOperation(value = "Gets all rooms info")
      @ResponseStatus(HttpStatus.OK)
      public Page<RoomResponseDto> getAll(@QuerydslPredicate(root = Room.class) Predicate predicate,
                                          @PageableDefault(size = Constants.PAGINATION_SIZE, sort="createdAt", direction = Sort.Direction.ASC) Pageable pageable){


            return roomService.findAllRooms(predicate, pageable);
      }

      @PostMapping("/buildings/{buildingId}/rooms/")
      @ApiOperation(value = "Creates and persists a rooms info")
      @ResponseStatus(HttpStatus.CREATED)
      public RoomResponseDto saveRoom(@PathVariable UUID buildingId, @RequestBody @Valid RoomDto room){
            log.debug("[X] Request to create new room");
            return roomService.saveRoom(buildingId, room);
      }

      @PutMapping("/rooms/{roomId}/")
      @ApiOperation(value = "Updates a specific rooms info")
      @ResponseStatus(HttpStatus.OK)
      public RoomResponseDto updateRoom(@PathVariable UUID roomId, @RequestBody @Valid RoomDto room){
            log.debug("[X] Request to update room with id={}", roomId);
            return roomService.updateRoom(roomId, room);
      }

      @DeleteMapping("/rooms/{roomId}/")
      @ApiOperation(value = "Deletes a specific rooms info")
      @ResponseStatus(HttpStatus.OK)
      public Response deleteRoom(@PathVariable UUID roomId){
            log.debug("[X] Request to delete room with id={}", roomId);
            roomService.deleteRoom(roomId);
            return new Response("Room has been deleted");
      }
}
