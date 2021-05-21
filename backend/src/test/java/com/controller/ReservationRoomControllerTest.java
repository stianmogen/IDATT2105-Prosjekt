package com.controller;

import com.factories.ReservationFactory;
import com.factories.SectionFactory;
import com.factories.UserFactory;
import com.model.*;
import com.repository.*;
import com.security.UserDetailsImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ReservationRoomControllerTest {

    private String URI = "/rooms/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    private Reservation reservation;
    private User user;
    private Section section;
    private Room room;
    private Building building;
    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() throws Exception {

        user = new UserFactory().getObject();
        section = new SectionFactory().getObject();
        assert section != null;
        room = section.getRoom();
        building = room.getBuilding();
        userRepository.save(user);
        buildingRepository.save(building);
        roomRepository.save(room);
        sectionRepository.save(section);
        reservation = new ReservationFactory().getObjectWithUserAndSection(user, section);

        userDetails = UserDetailsImpl.builder().email(user.getEmail()).build();

        reservationRepository.save(reservation);
    }

    @AfterEach
    void cleanUp() throws Exception {
        reservationRepository.deleteAll();
        sectionRepository.deleteAll();
        buildingRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(value = "spring")
    void testGetReservationById() throws Exception {

        UserDetailsImpl userDetails2;
        User user2 = new UserFactory().getObject();
        assert user2 != null;
        Section section2 = new SectionFactory().getObject();
        assert section2 != null;
        Room room2 = section2.getRoom();
        Building building2 = room2.getBuilding();
        userRepository.save(user2);
        buildingRepository.save(building2);
        roomRepository.save(room2);
        sectionRepository.save(section2);
        Reservation reservation2 = new ReservationFactory().getObjectWithUserAndSection(user2, section2);

        reservationRepository.save(reservation2);

        userDetails2 = UserDetailsImpl.builder().email(user2.getEmail()).build();

        mockMvc.perform(get(URI + room2.getId() + "/reservations/")
                .accept(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].startTime").value("hore"))
                .andExpect(jsonPath("$.[0].id").value(reservation2.getId().toString()));
    }


}