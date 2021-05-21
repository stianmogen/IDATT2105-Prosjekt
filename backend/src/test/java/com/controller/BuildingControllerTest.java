package com.controller;


import com.factories.BuildingFactory;
import com.factories.UserFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Building;
import com.model.User;
import com.repository.BuildingRepository;
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
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BuildingControllerTest {

    private String URI = "/buildings/";

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoleUtil roleUtil;

    private Building building;

    private User admin;

    private User user;

    private UserDetails adminDetails;

    private UserDetails userDetails;


    @BeforeEach
    void setup() throws Exception {

        building = new BuildingFactory().getObject();
        assert building != null;
        building = buildingRepository.save(building);

        admin = new UserFactory().getObject();
        assert admin != null;
        admin = roleUtil.setRoleToAdmin(admin);
        admin = userRepository.save(admin);
        adminDetails = userDetailsService.loadUserByUsername(admin.getEmail());

        user = new UserFactory().getObject();
        assert user != null;
        user = roleUtil.setRoleToUser(user);
        user = userRepository.save(user);
        userDetails = userDetailsService.loadUserByUsername(user.getEmail());
    }

    @AfterEach
    void cleanup() {
        buildingRepository.deleteAll();
    }

    @Test
    @WithMockUser(value = "spring")
    void testGetBuildingById() throws Exception {
        mockMvc.perform(get(URI + building.getId() + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(adminDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(building.getName()));
    }

    @Test
    @WithMockUser(value = "spring")
    void testFindAllBuildingsWithAuthorization() throws Exception {
        mockMvc.perform(get(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(adminDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[*].name", hasItem(building.getName())));
    }


    @Test
    @WithMockUser(value = "spring")
    void testCreateBuildingWithRoleAdminAndExpectCreated() throws Exception {

        Building testBuilding = new BuildingFactory().getObject();
        mockMvc.perform(post(URI)
                .with(user(adminDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBuilding)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(testBuilding.getName()));
    }

    @Test
    @WithMockUser(value = "spring")
    void testCreateBuildingWithRoleUserAndExpectForbidden() throws Exception {
        Building testBuilding = new BuildingFactory().getObject();
        mockMvc.perform(post(URI)
              .with(user(userDetails))
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(testBuilding)))
              .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = "spring")
    void testDeleteBuildingWithRoleAdminAndExpectOk() throws Exception {

        Building testBuilding = new BuildingFactory().getObject();
        assert testBuilding != null;
        testBuilding = buildingRepository.save(testBuilding);


        mockMvc.perform(delete(URI + testBuilding.getId() + "/")
                .with(user(adminDetails))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Building has been deleted"));
    }

    @Test
    @WithMockUser(value = "spring")
    void testDeleteBuildingWithRoleUserAndExpectForbidden() throws Exception {

        Building testBuilding = new BuildingFactory().getObject();
        assert testBuilding != null;
        testBuilding = buildingRepository.save(testBuilding);


        mockMvc.perform(delete(URI + testBuilding.getId() + "/")
              .with(user(userDetails))
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(value = "spring")
    void testUpdateBuildingWithRoleAdminAndExpectOk() throws Exception {

        building.setName(getRandomString(10));

        mockMvc.perform(put(URI + building.getId() + "/")
                .with(user(adminDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(building)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(building.getId().toString()));
    }

    @Test
    @WithMockUser(value = "spring")
    void testUpdateBuildingWithRoleUserAndExpectForbidden() throws Exception {

        building.setName(getRandomString(10));

        mockMvc.perform(put(URI + building.getId() + "/")
              .with(user(userDetails))
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(building)))
              .andExpect(status().isForbidden());
    }
}
