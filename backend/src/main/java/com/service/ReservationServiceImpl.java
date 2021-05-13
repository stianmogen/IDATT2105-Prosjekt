package com.service;

import com.dto.ReservationDto;
import com.exception.EntityNotFoundException;
import com.exception.UserNotFoundException;
import com.model.Reservation;
import com.model.ReservationId;
import com.model.Section;
import com.model.User;
import com.querydsl.core.types.Predicate;
import com.repository.ReservationRepository;
import com.repository.RoomRepository;
import com.repository.SectionRepository;
import com.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    ModelMapper modelMapper = new ModelMapper();

    ReservationRepository reservationRepository;

    UserRepository userRepository;

    RoomRepository roomRepository;

    SectionRepository sectionRepository;


    @Override
    public ReservationDto saveReservation(UUID userId, UUID sectionId, ZonedDateTime from, ZonedDateTime to, int participants) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Section section = sectionRepository.findById(sectionId).orElseThrow(EntityNotFoundException::new);
        Reservation reservation = reservationRepository.save(new Reservation(new ReservationId(userId, sectionId), user, section, participants, from, to));
        return modelMapper.map(reservation, ReservationDto.class);
    }

    @Override
    public Page<ReservationDto> getReservationsForRoom(Predicate predicate, Pageable pageable, UUID roomId) {
        return null;
    }

    @Override
    public Page<ReservationDto> getReservationsForUser(Predicate predicate, Pageable pageable, UUID userId) {
        return null;
    }

    @Override
    public ReservationDto removeReservation(ReservationId reservationId) {
        return null;
    }
}
