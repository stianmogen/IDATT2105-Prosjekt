package com.controller;

import com.dto.CreateReservationDto;
import com.factories.ReservationFactory;
import com.factories.SectionFactory;
import com.factories.UserFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Reservation;
import com.model.Section;
import com.model.User;
import com.repository.*;
import com.service.UserDetailsServiceImpl;
import com.utils.RoleUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
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

    ModelMapper modelMapper = new ModelMapper();

    private Reservation reservation;
    private User user;
    private Section section;
    private UserDetails userDetails;
    private User admin;
    private UserDetails adminDetails;

    @BeforeEach
    void setUp() throws Exception {
        user = new UserFactory().getObject();

        user = roleUtil.setRoleToUser(user);
        user = userRepository.save(user);
        userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        admin = new UserFactory().getObject();
        assert admin != null;
        admin = roleUtil.setRoleToAdmin(admin);
        admin = userRepository.save(admin);
        adminDetails = userDetailsService.loadUserByUsername(admin.getEmail());

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
                .with(user(userDetails)))
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
        testSection = sectionRepository.save(testSection);

        CreateReservationDto res = new CreateReservationDto();

        res.setStartTime(ZonedDateTime.now());
        res.setEndTime(ZonedDateTime.now());
        res.setSectionsIds(List.of(testSection.getId()));
        res.setParticipants(1);


        mockMvc.perform(post(URI)
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(res)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.participants").value(res.getParticipants()));

    }

    @Test
    @WithMockUser(value = "spring")
    void testDeleteReservationAsUserAndGetForbidden() throws Exception {

        mockMvc.perform(delete(URI + reservation.getId() + "/")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(value = "spring")
    void testDeleteReservationAsAdminAndReturn200Ok() throws Exception {

        mockMvc.perform(delete(URI + reservation.getId() + "/")
                .with(user(adminDetails))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Reservation has been deleted"));

    }
}
