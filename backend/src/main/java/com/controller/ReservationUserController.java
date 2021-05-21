package com.controller;

import com.dto.CreateReservationDto;
import com.dto.ReservationDto;
import com.model.Reservation;
import com.querydsl.core.types.Predicate;
import com.security.UserDetailsImpl;
import com.service.ReservationService;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/users/me/reservations/")
public class ReservationUserController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("{reservationId}")
    @ApiOperation(value = "Gets a spesific reservations info")
    @ResponseStatus(HttpStatus.OK)
    public ReservationDto getReservationById(@PathVariable UUID reservationId){
        log.debug("[X] Request to get reservation with id={}", reservationId);
        return reservationService.getReservationById(reservationId);
    }

    @GetMapping
    @ApiOperation(value = "Gets all reservation for a logged in user")
    @ResponseStatus(HttpStatus.OK)
    public Page<ReservationDto> getReservationForUser(@QuerydslPredicate(root = Reservation.class) Predicate predicate,
                                                      @PageableDefault(size = Constants.PAGINATION_SIZE, sort="startTime", direction = Sort.Direction.ASC)Pageable pageable,
                                                      Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.debug("[X] Request to get all reservations for user with username={}", userDetails.getUsername());
        return reservationService.getReservationsForUser(predicate, pageable, userDetails.getUsername());
    }

    @PostMapping
    @ApiOperation(value = "Creates and persist a reservation")
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDto postReservation(Authentication authentication, @Valid @RequestBody CreateReservationDto reservation) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        log.debug("[x] Request from {} to create a reservation", userDetails.getUsername());
        reservation.setUserId(userDetails.getId());
        return reservationService.saveReservation(reservation);
    }

    @DeleteMapping("{reservationId}/")
    @ApiOperation(value = "Deletes a reservation info")
    @ResponseStatus(HttpStatus.OK)
    public Response deleteReservationById(@PathVariable UUID reservationId){
        log.debug("[X] Request to delete Reservation with id={}", reservationId);
        reservationService.removeReservation(reservationId);
        return new Response("Reservation has been deleted");
    }
}
