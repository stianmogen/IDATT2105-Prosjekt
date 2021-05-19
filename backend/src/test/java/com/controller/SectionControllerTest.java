package com.controller;

import com.factories.SectionFactory;
import com.model.Building;
import com.model.Room;
import com.model.Section;
import com.model.User;
import com.repository.BuildingRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SectionControllerTest {

    private String URI = "/sections/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    private Section section;
    private Room room;
    private User user;
    private Building building;

    @BeforeEach
    void setup() throws Exception {

        section = new SectionFactory().getObject();
        assert section != null;

        room = section.getRoom();
        assert room != null;
        building = room.getBuilding();
        assert building != null;

        buildingRepository.save(building);
        roomRepository.save(room);
        sectionRepository.save(section);

    }

    @AfterEach
    void cleanup() {
        sectionRepository.deleteAll();
        roomRepository.deleteAll();
        buildingRepository.deleteAll();
    }


    @Test
    @WithMockUser(value = "spring")
    public void userControllerTestGetSectionById() throws Exception {

        mockMvc.perform(get(URI + section.getId() + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(section.getId().toString()));

    }

    @Test
    @WithMockUser(value = "spring")
    public void userControllerTestUpdateSection() throws Exception {



    }

    @Test
    @WithMockUser(value = "spring")
    public void userControllerTestCreateSection() throws Exception {

    }

    @Test
    @WithMockUser(value = "spring")
    public void userControllerTestDeleteSection() throws Exception {

        
    }

}
