package com.controller;

import com.dto.ReservationDto;
import com.dto.UserEmailDto;
import com.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/rooms/{roomId}/reservations/")
public class ReservationRoomController {
    @Autowired
    private ReservationService reservationService;

    //@PostMapping
    //public ReservationDto postReservation(@PathVariable UUID sectionId, @RequestBody UserEmailDto userEmailDto)
}
