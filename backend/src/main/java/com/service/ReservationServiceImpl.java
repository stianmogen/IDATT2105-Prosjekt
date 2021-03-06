package com.service;

import com.dto.CreateReservationDto;
import com.dto.ReservationDto;
import com.exception.ReservationNotFoundException;
import com.exception.SectionNotFoundException;
import com.exception.UserNotFoundException;
import com.model.QReservation;
import com.model.Reservation;
import com.model.Section;
import com.model.User;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.repository.ReservationRepository;
import com.repository.RoomRepository;
import com.repository.SectionRepository;
import com.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReservationServiceImpl implements ReservationService {


    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private RoomService roomService;


    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public ReservationDto saveReservation(CreateReservationDto reservationDto) {
        User user = userRepository.findById(reservationDto.getUserId()).orElseThrow(UserNotFoundException::new);
        List<UUID> sectionIds = reservationDto
              .getSectionsIds()
              .stream()
              .distinct()
              .collect(Collectors.toList());

        List<Section> sections = new ArrayList<>();
        sectionIds.forEach(id -> sections.add(sectionRepository.findAvailableSection(id, reservationDto.getStartTime(), reservationDto.getEndTime()).orElseThrow(SectionNotFoundException::new)));
        Reservation reservation = modelMapper.map(reservationDto, Reservation.class);
        reservation.setUser(user);
        reservation.setId(UUID.randomUUID());
        reservation.setSections(sections);

        Reservation savedReservation = reservationRepository.save(reservation);
        return modelMapper.map(savedReservation, ReservationDto.class);
    }

    @Override
    public List<ReservationDto> getReservationsForRoom(Predicate predicate, Pageable pageable, UUID roomId) {

        List<Section> sections = sectionService.getSectionByRoomId(roomId);

        List<Reservation> reservations = sections.stream()
              .map(p -> reservationRepository.findReservationsBySectionsContains(p))
              .flatMap(List::stream)
              .collect(Collectors.toList());

        return reservations.stream().map(p -> modelMapper.map(p, ReservationDto.class)).collect(Collectors.toList());
    }


    public ReservationDto mapReservation(Reservation reservation){
        return modelMapper.map(reservation, ReservationDto.class);
    }

    @Override
    public Page<ReservationDto> getReservationsForUser(Predicate predicate, Pageable pageable, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        QReservation qReservation = QReservation.reservation;
        predicate = ExpressionUtils.allOf(predicate, qReservation.user.email.eq(user.getEmail()));

        Page<Reservation> reservationsFound = reservationRepository.findAll(predicate, pageable);
        return reservationsFound.map(p -> modelMapper.map(p, ReservationDto.class));
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
