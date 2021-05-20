package com.controller;

import com.factories.SectionFactory;
import com.factories.UserFactory;
import com.model.*;
import com.repository.*;
import com.service.RoomService;
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

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SectionRepository sectionRepository;

    private User user;
    private Section section;
    private Building building;
    private Room room;

    private User user2;
    private Section section2;
    private Building building2;
    private Room room2;
    private Reservation reservation;


    @BeforeEach
    public void setUp() throws Exception {

        user = new UserFactory().getObject();
        section = new SectionFactory().getObject();
        assert section != null;
        room = section.getRoom();
        building = room.getBuilding();
        userRepository.save(user);
        buildingRepository.save(building);
        roomRepository.save(room);
        sectionRepository.save(section);

        user2 = new UserFactory().getObject();
        section2 = new SectionFactory().getObject();
        assert section2 != null;

        room2 = section2.getRoom();
        room2.setBuilding(building);
        //building2 = room2.getBuilding();
        userRepository.save(user2);
        //buildingRepository.save(building2);
        roomRepository.save(room2);
        sectionRepository.save(section2);

        reservation = Reservation.builder().startTime(ZonedDateTime.now())
                .endTime(ZonedDateTime.now().plusDays(10))
                .sections(List.of(section2))
                .id(UUID.randomUUID())
                .participants(1)
                .user(user2)
                .build();

        section2.setReservations(List.of(reservation));

        reservationRepository.save(reservation);
        sectionRepository.save(section2);
    }

    @AfterEach
    public void cleanUp(){
        reservationRepository.deleteAll();
        sectionRepository.deleteAll();
        roomRepository.deleteAll();
        buildingRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(value = "spring")
    void testGetRoomByID() throws Exception {
        mockMvc.perform(get("/rooms/"+room.getId()+"/")
                .accept(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(room.getId().toString()))
                .andExpect(jsonPath("$.name").value(room.getName()));
    }

    @Test
    @WithMockUser(value = "spring")
    void testGetAllReturnsAll() throws Exception {
        mockMvc.perform(get("/rooms/")
                .accept(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.[0].name").value(room.getName()))
                .andExpect(jsonPath("$.content.[1].name").value(room2.getName()))
                .andExpect(jsonPath("$.content.[0].id").value(room.getId().toString()))
                .andExpect(jsonPath("$.content.[1].id").value(room2.getId().toString()));
    }

    @Test
    @WithMockUser(value = "spring")
    void testGetAllWithFilteringReturnsFilteredRoomsOne() throws Exception {
        mockMvc.perform(get("/rooms/")
                .accept(MediaType.APPLICATION_JSON).with(csrf())
                .param("buildingId", String.valueOf(room.getBuilding().getId()))
                .param("startTime", String.valueOf(reservation.getStartTime().minusDays(10000)))
                .param("endTime", String.valueOf(reservation.getEndTime().plusDays(100000)))
                .param("participants", "0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.[0].name").value(room.getName()))
                .andExpect(jsonPath("$.content.length()").value(1));
    }
}