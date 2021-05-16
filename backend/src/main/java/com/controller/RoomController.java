package com.controller;

import com.dto.RoomResponseDto;
import com.dto.RoomDto;
import com.dto.RoomDto;
import com.dto.RoomResponseDto;
import com.model.Room;
import com.service.RoomService;
import com.utils.Constants;
import com.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

      @GetMapping("/rooms/{roomId}")
      @ResponseStatus(HttpStatus.OK)
      public RoomResponseDto getRoomById(@PathVariable UUID roomId){
            log.debug("[X] Request to get room with id={}", roomId);
            return roomService.getRoomById(roomId);
      }

      @PostMapping("/buildings/{buildingId}/rooms")
      @ResponseStatus(HttpStatus.CREATED)
      public RoomResponseDto saveRoom(@PathVariable UUID buildingId, @RequestBody @Valid RoomDto room){
            log.debug("[X] Request to create new room");
            return roomService.saveRoom(buildingId, room);
      }

      @PutMapping("rooms/{roomId}")
      @ResponseStatus(HttpStatus.OK)
      public RoomResponseDto updateRoom(@PathVariable UUID roomId, @RequestBody @Valid RoomDto room){
            log.debug("[X] Request to update room with id={}", roomId);
            return roomService.updateRoom(roomId, room);
      }

      @DeleteMapping("rooms/{roomId}")
      @ResponseStatus(HttpStatus.OK)
      public Response deleteRoom(@PathVariable UUID roomId){
            log.debug("[X] Request to delete room with id={}", roomId);
            roomService.deleteRoom(roomId);
            return new Response("Room has been deleted");
      }
}
