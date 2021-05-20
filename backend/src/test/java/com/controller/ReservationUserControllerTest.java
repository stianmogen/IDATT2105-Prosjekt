package com.controller;

import com.factories.ReservationFactory;
import com.factories.SectionFactory;
import com.factories.UserFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Reservation;
import com.model.Section;
import com.model.User;
import com.repository.*;
import com.security.UserDetailsImpl;
import com.service.UserDetailsServiceImpl;
import com.utils.RoleUtil;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ReservationUserControllerTest {

    private String URI = "/users/me/reservations/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoleUtil roleUtil;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Reservation reservation;
    private User user;
    private Section section;
    private UserDetails userDetails;


    @BeforeEach
    void setUp() throws Exception {
        user = new UserFactory().getObject();

        user = roleUtil.setRoleToUser(user);
        user = userRepository.save(user);
        userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        section = new SectionFactory().getObject();
        buildingRepository.save(section.getRoom().getBuilding());
        roomRepository.save(section.getRoom());
        sectionRepository.save(section);
        reservation = new ReservationFactory().getObjectWithUserAndSection(user, section);

        reservationRepository.save(reservation);
    }

    @AfterEach
    void cleanUp() {
        reservationRepository.deleteAll();;
        userRepository.deleteAll();
        sectionRepository.deleteAll();
        roomRepository.deleteAll();
    }

    @Test
    @WithMockUser(value = "spring")
    void testGetReservationById() throws Exception {

        mockMvc.perform(get(URI + reservation.getId() + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reservation.getId().toString()));
    }

    @Test
    @WithMockUser(value = "spring")
    void testGetReservationForUser() throws Exception {


        mockMvc.perform(get(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[0].id").value(reservation.getId().toString()));

    }

    @Test
    @WithMockUser(value = "spring")
    void testPostReservation() throws Exception {

        Section testSection = new SectionFactory().getObject();
        buildingRepository.save(testSection.getRoom().getBuilding());
        roomRepository.save(testSection.getRoom());
        sectionRepository.save(testSection);
        Reservation testReservation = new ReservationFactory().getObjectWithUserAndSection(user, testSection);

        mockMvc.perform(post(URI)
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testReservation)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(testSection.getName()));

    }

}
