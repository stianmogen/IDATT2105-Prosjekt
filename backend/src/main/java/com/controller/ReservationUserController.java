package com.controller;

import com.dto.CreateReservationDto;
import com.dto.ReservationDto;
import com.model.Reservation;
import com.querydsl.core.types.Predicate;
import com.service.ReservationService;
import com.utils.Response;
import com.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/reservations/")
public class ReservationUserController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("{reservationId}")
    @ResponseStatus(HttpStatus.OK)
    public ReservationDto getReservationById(@PathVariable UUID reservationId){
        log.debug("[X] Request to get reservation with id={}", reservationId);
        return reservationService.getReservationById(reservationId);
    }

    @GetMapping({""})
    @ResponseStatus(HttpStatus.OK)
    public Page<ReservationDto> getReservationForUser(@QuerydslPredicate(root = Reservation.class) Predicate predicate,
                                                      @PageableDefault(size = Constants.PAGINATION_SIZE, sort="startTime", direction = Sort.Direction.ASC)Pageable pageable,
                                                      Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.debug("[X] Request to get all reservations for user with username={}", userDetails.getUsername());
        return reservationService.getReservationsForUser(predicate, pageable, userDetails.getUsername());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDto postReservation(Authentication authentication, @Valid @RequestBody CreateReservationDto reservation) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.debug("[x] Request from {} to create a reservation", userDetails.getUsername());
        return reservationService.saveReservation(reservation, userDetails.getUsername());
    }

    @DeleteMapping("{reservationId}")
    @ResponseStatus(HttpStatus.OK)
    public Response deleteReservationById(@PathVariable UUID reservationId){
        log.debug("[X] Request to delete Reservation with id={}", reservationId);
        reservationService.removeReservation(reservationId);
        return new Response("Reservation has been deleted");
    }
}
