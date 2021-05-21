package com.controller;

import com.dto.CreateReservationDto;
import com.dto.ReservationDto;
import com.dto.UserEmailDto;
import com.model.Reservation;
import com.querydsl.core.types.Predicate;
import com.security.UserDetailsImpl;
import com.service.ReservationService;
import com.utils.Constants;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/rooms/{roomId}/reservations/")
public class ReservationRoomController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping
    @ApiOperation(value = "Creates and persists a reservation")
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDto postReservation(Authentication authentication, @PathVariable UUID roomId, @RequestBody CreateReservationDto createReservationDto){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        createReservationDto.setUserId(userDetails.getId());
        log.info("[X] Request to Post Reservation in room {} with userId={}", roomId, createReservationDto.getUserId());
        log.info(String.valueOf(createReservationDto.getSectionsIds().size()));
        return reservationService.saveReservation(createReservationDto);
    }

    @GetMapping
    @ApiOperation(value = "Gets all reservations for a given room")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDto> getReservationsForRoom(@QuerydslPredicate(root = Reservation.class) Predicate predicate,
                                                @PageableDefault(size = Constants.PAGINATION_SIZE, sort="reservation.startDate", direction = Sort.Direction.ASC) Pageable pageable,
                                                @PathVariable UUID roomId){
        log.debug("[X] Request to get reservations for room with id={}", roomId);
        return reservationService.getReservationsForRoom(predicate, pageable, roomId);
    }
}
