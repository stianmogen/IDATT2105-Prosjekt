package com.controller;

import com.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDto postRegistration(@RequestBody ReservationDto reservation) {
        try{

        }catch (UserNotFoundException | ActivityNotFoundException ex){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage());
        }catch (ActivityFullExecption ex){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        log.debug("[X] Request to Post Registration with userid={}", user.getEmail());
        return registrationService.saveRegistration(user.getId(), activityId);
    }
}
