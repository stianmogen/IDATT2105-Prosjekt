package com.controller;

import com.factories.SectionFactory;
import com.factories.UserFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Building;
import com.model.Room;
import com.model.Section;
import com.model.User;
import com.repository.BuildingRepository;
import com.repository.RoomRepository;
import com.repository.SectionRepository;
import com.repository.UserRepository;
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

import static com.utils.StringRandomizer.getRandomString;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SectionControllerTest {

    private String URI = "/sections/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private RoleUtil roleUtil;

    private Section section;
    private Room room;
    private User admin;
    private Building building;
    private UserDetails adminDetails;

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

        admin = new UserFactory().getObject();
        assert admin != null;
        admin = roleUtil.setRoleToAdmin(admin);
        admin = userRepository.save(admin);
        adminDetails = userDetailsService.loadUserByUsername(admin.getEmail());

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

        section.setName(getRandomString(10));

        mockMvc.perform(put(URI + section.getId() + "/")
                .with(user(adminDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(section)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(section.getId().toString()));


    }

    @Test
    @WithMockUser(value = "spring")
    public void userControllerTestCreateSection() throws Exception {

        section = new SectionFactory().getObject();
        assert section != null;

        room = section.getRoom();
        assert room != null;
        building = room.getBuilding();
        assert building != null;

        buildingRepository.save(building);
        roomRepository.save(room);


        mockMvc.perform(post("/rooms/"+ room.getId() + URI)
                .with(user(adminDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(section)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(section.getName()));


    }

    @Test
    @WithMockUser(value = "spring")
    public void userControllerTestDeleteSection() throws Exception {

        section = new SectionFactory().getObject();
        assert section != null;

        room = section.getRoom();
        assert room != null;
        building = room.getBuilding();
        assert building != null;

        buildingRepository.save(building);
        roomRepository.save(room);
        sectionRepository.save(section);

        mockMvc.perform(delete(URI + section.getId() + "/")
                .with(user(adminDetails))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Section has been deleted"));


        
    }

}
