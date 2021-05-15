package com.service;

import com.dto.CreateReservationDto;
import com.dto.ReservationDto;
import com.exception.ReservationNotFoundException;
import com.exception.SectionNotFoundException;
import com.exception.UserNotFoundException;
import com.model.Reservation;
import com.model.Section;
import com.model.User;
import com.querydsl.core.types.Predicate;
import com.repository.ReservationRepository;
import com.repository.RoomRepository;
import com.repository.SectionRepository;
import com.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {


    ModelMapper modelMapper = new ModelMapper();

    ReservationRepository reservationRepository;

    UserRepository userRepository;

    RoomRepository roomRepository;

    SectionRepository sectionRepository;

    SectionService sectionService;


    @Override
    public ReservationDto saveReservation(CreateReservationDto reservationDto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        List<UUID> sectionIds = reservationDto
              .getSectionsIds()
              .stream()
              .distinct()
              .collect(Collectors.toList());

        List<Section> sections = new ArrayList<>();
        sectionIds.forEach(id -> sections.add(sectionRepository.findAvailableSection(id, reservationDto.getFrom(), reservationDto.getTo()).orElseThrow(SectionNotFoundException::new)));
        Reservation reservation = modelMapper.map(reservationDto, Reservation.class);
        reservation.setUser(user);
        reservation.setSections(sections);

        Reservation savedReservation = reservationRepository.save(reservation);
        return modelMapper.map(savedReservation, ReservationDto.class);
    }

    @Override
    public List<ReservationDto> getReservationsForRoom(Predicate predicate, Pageable pageable, UUID roomId) {
        List<Section> sections = sectionRepository.findAllByRoomId(roomId);
        List<Reservation> reservations = sections.stream()
              .map(p -> reservationRepository.findReservationsBySectionsContains(p))
              .flatMap(List::stream)
              .collect(Collectors.toList());
        return reservations.stream().map(p -> modelMapper.map(p, ReservationDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ReservationDto> getReservationsForUser(Predicate predicate, Pageable pageable, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        List<Reservation> reservationsFound = reservationRepository.findReservationsByUserId(user.getId());
        List<ReservationDto> reservationDtos = reservationsFound.stream()
              .map(p -> modelMapper.map(p, ReservationDto.class))
              .collect(Collectors.toList());

        return reservationDtos;
    }

    @Override
    public ReservationDto getReservationById(UUID reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(ReservationNotFoundException::new);
        return modelMapper.map(reservation, ReservationDto.class);
    }

    @Override
    public void removeReservation(UUID reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(ReservationNotFoundException::new);
        reservationRepository.delete(reservation);
    }
}
