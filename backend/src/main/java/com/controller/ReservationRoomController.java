package com.controller;

import com.dto.CreateReservationDto;
import com.dto.ReservationDto;
import com.dto.UserEmailDto;
import com.model.Reservation;
import com.querydsl.core.types.Predicate;
import com.service.ReservationService;
import com.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/rooms/{roomId}/reservations/")
public class ReservationRoomController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDto postReservation(@RequestBody CreateReservationDto createReservationDto, @RequestBody UserEmailDto userEmailDto){
        log.debug("[X] Request to Post Reservation with user={}", userEmailDto.getEmail());
        return reservationService.saveReservation(createReservationDto, userEmailDto.getEmail());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDto> getReservationsForRoom(@QuerydslPredicate(root = Reservation.class) Predicate predicate,
                                                @PageableDefault(size = Constants.PAGINATION_SIZE, sort="activity.startDate", direction = Sort.Direction.ASC) Pageable pageable,
                                                @PathVariable UUID roomId){
        log.debug("[X] Request to get reservations for room with id={}", roomId);
        return reservationService.getReservationsForRoom(predicate, pageable, roomId);
    }
}
