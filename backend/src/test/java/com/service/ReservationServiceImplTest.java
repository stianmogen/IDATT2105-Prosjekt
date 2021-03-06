package com.service;

import com.dto.ReservationDto;
import com.factories.ReservationFactory;
import com.factories.SectionFactory;
import com.factories.UserFactory;
import com.model.Reservation;
import com.model.Section;
import com.model.User;
import com.querydsl.core.types.Predicate;
import com.repository.ReservationRepository;
import com.repository.RoomRepository;
import com.repository.SectionRepository;
import com.repository.UserRepository;
import com.utils.ListingUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTest {

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private SectionService sectionService;

    ModelMapper modelMapper = new ModelMapper();

    private User user;
    private User secondUser;
    private Section section;
    private Reservation reservation;
    List<Reservation> reservations;


    private Predicate predicate;
    private Pageable pageable;

    @BeforeEach
    void setUp() throws Exception {
        user = new UserFactory().getObject();
        section = new SectionFactory().getObject();

        reservation = new ReservationFactory().getObjectWithUserAndSection(user, section);
        reservationRepository.save(reservation);
        reservations = List.of(reservation);

        predicate = ListingUtils.getEmptyPredicate();
        pageable = ListingUtils.getDefaultPageable();
    }

    @AfterEach
    void cleanUp() {
        reservationRepository.deleteAll();
    }

    @Test
    void testGetReservationForUser(){
        when(reservationRepository.findAll(any(Predicate.class), any(PageRequest.class))).thenReturn(new PageImpl<>(reservations, pageable, reservations.size()));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Page<ReservationDto> reservationDtos = reservationService.getReservationsForUser(predicate, pageable, user.getEmail());

        assertThat(reservationDtos.stream().findFirst()).isNotNull();
        assertThat(reservationDtos.stream().findFirst().get().getId()).isEqualTo(reservations.stream().findFirst().get().getId());
    }

    @Test
    void testReservationForRoom(){
        when(reservationRepository.findReservationsBySectionsContains(section)).thenReturn(reservations);
        when(sectionService.getSectionByRoomId(section.getRoom().getId())).thenReturn(List.of(section));
        List<ReservationDto> reservationDtos = reservationService.getReservationsForRoom(predicate, pageable, section.getRoom().getId());

        assertThat(reservationDtos.stream().findFirst()).isNotNull();
        assertThat(reservationDtos.stream().findFirst().get().getId()).isEqualTo(reservations.stream().findFirst().get().getId());
    }

    @Test
    void testReservationById(){
        when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation));
        assertThat(reservationService.getReservationById(reservation.getId()).getId()).isEqualTo(reservation.getId());
    }
}
