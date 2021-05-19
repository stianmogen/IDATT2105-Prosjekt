package com.controller;


import com.common.UserPrivilege;
import com.common.UserRole;
import com.factories.BuildingFactory;
import com.factories.UserFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Building;
import com.model.Privilege;
import com.model.Role;
import com.model.User;
import com.repository.BuildingRepository;
import com.repository.PrivilegeRepository;
import com.repository.RoleRepository;
import com.repository.UserRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.utils.StringRandomizer.getRandomString;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BuildingControllerTest {

    private String URI = "/buildings/";

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Building building;

    private User user;

    @BeforeEach
    void setup() throws Exception {

        building = new BuildingFactory().getObject();
        assert building != null;
        building = buildingRepository.save(building);

        user = new UserFactory().getObject();
        assert user != null;
        user = setUserToAdmin(user);
        user = userRepository.save(user);
        System.out.println(user.getRoles().size() + "asdddddddddddddddddddddddddd!!!!!!!!!!!!!!!!!                 !!!!!!!!!!!!!!!!!!!!!!!Ddddddddddddddddddddd");

    }

    @AfterEach
    void cleanup() {
        buildingRepository.deleteAll();
    }

    @Test
    @WithMockUser(value = "spring")
    void testBuildingControllerGetBuildingById() throws Exception {

        UserDetails userDetails = UserDetailsImpl.builder().email(user.getEmail()).id(user.getId()).build();
        mockMvc.perform(get(URI + building.getId() + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(building.getName()));
    }

    @Test
    @WithMockUser(value = "spring")
    void testBuildingControllerFindAllBuildings() throws Exception {

        mockMvc.perform(get(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[*].name", hasItem(building.getName())));
    }

    @Test
    @WithMockUser(value = "spring")
    void testBuildingControllerCreateBuilding() throws Exception {

        Building testBuilding = new BuildingFactory().getObject();
        UserDetails userDetails = UserDetailsImpl.builder().email(user.getEmail()).id(user.getId()).build();
        mockMvc.perform(post(URI)
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBuilding)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(testBuilding.getName()));

    }

    @Test
    @WithMockUser(value = "spring")
    void testBuildingControllerDeleteBuilding() throws Exception {

        Building testBuilding = new BuildingFactory().getObject();
        assert testBuilding != null;
        testBuilding = buildingRepository.save(testBuilding);


        mockMvc.perform(delete(URI + testBuilding.getId() + "/")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Building has been deleted"));

    }


    @Test
    @WithMockUser(value = "spring")
    void testBuildingControllerUpdateBuilding() throws Exception {

        building.setName(getRandomString(10));

        mockMvc.perform(put(URI + building.getId() + "/")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(building)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(building.getId().toString()));
    }




    private User setUserToAdmin(User user) {
        Privilege readPrivilege = createPrivilegeIfNotFound(UserPrivilege.READ);
        Privilege writePrivilege = createPrivilegeIfNotFound(UserPrivilege.WRITE);
        Privilege addUserPrivilege = createPrivilegeIfNotFound(UserPrivilege.ADD_USER);

        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege, addUserPrivilege);

        createRoleIfNotFound(UserRole.ADMIN, adminPrivileges);
        createRoleIfNotFound(UserRole.USER, Collections.singletonList(readPrivilege));

        Optional<Role> adminRole = roleRepository.findByName(UserRole.ADMIN);
        adminRole.ifPresent(role -> user.setRoles(Collections.singletonList(role)));
        return user;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {
        Optional<Privilege> existingPrivilege = privilegeRepository.findByName(name);
        if (existingPrivilege.isEmpty()) {
            Privilege privilege = new Privilege();
            privilege.setName(name);
            return privilegeRepository.save(privilege);
        }
        return existingPrivilege.get();
    }

    @Transactional
    Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {

        Optional<Role> existingRole = roleRepository.findByName(name);
        if (existingRole.isEmpty()) {
            Role role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            return roleRepository.save(role);
        }
        return existingRole.get();
    }
}
