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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.lenient;
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

    @Mock
    @Autowired
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

    @Test
    void testGetReservationForUser(){
        lenient().when(reservationRepository.findReservationsByUserId(user.getId())).thenReturn(reservations);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        List<ReservationDto> reservationDtos = reservationService.getReservationsForUser(predicate, pageable, user.getEmail());

        assertThat(reservationDtos.stream().findFirst()).isNotNull();
        assertThat(reservationDtos.stream().findFirst()).isEqualTo(reservations.stream().findFirst());
    }
}
