package com.controller;

import com.factories.ReservationFactory;
import com.factories.SectionFactory;
import com.factories.UserFactory;
import com.model.Reservation;
import com.model.Section;
import com.model.User;
import com.repository.ReservationRepository;
import com.repository.RoomRepository;
import com.repository.SectionRepository;
import com.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ReservationUserControllerTest {

    private String URI = "/reservations/";

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

    private Reservation reservation;
    private User user;
    private Section section;

    @BeforeEach
    void setUp() throws Exception {
        user = new UserFactory().getObject();
        section = new SectionFactory().getObject();
        userRepository.save(user);
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
                .contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reservation.getId().toString()));
    }


}
