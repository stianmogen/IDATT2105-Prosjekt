package com.controller;

import com.factories.UserFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.User;
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

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.utils.StringRandomizer.getRandomString;


@SpringBootTest(webEnvironment = MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    private String URI = "/users/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private User user;

    private UserFactory userFactory = new UserFactory();

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setUp() throws Exception {
        user = new UserFactory().getObject();
        assert user != null;

        user = userRepository.save(user);

    }

    @AfterEach
    public void cleanUp(){
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(value = "spring")
    public void userControllerTestGetUserById() throws Exception {

        mockMvc.perform(get(URI + user.getId() + "/")
                .contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()));

    }

    @Test
    @WithMockUser(value = "spring")
    public void userControllerTestGetAllUsers() throws Exception {

        mockMvc.perform(get(URI)
                .contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[*].firstName", hasItem(user.getFirstName())));

    }

    @Test
    @WithMockUser(value = "spring")
    public void userControllerTestGetMe() throws Exception {

        UserDetails userDetails = UserDetailsImpl.builder().email(user.getEmail()).build();

        mockMvc.perform(get(URI + "me/")
                .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId().toString()));
    }

    @Test
    @WithMockUser(value = "spring")
    public void userControllerTestUpdateUser() throws Exception {

        String surname = getRandomString(10);
        user.setSurname(surname);

        UserDetails userDetails = UserDetailsImpl.builder().email(user.getEmail()).id(user.getId()).build();
        mockMvc.perform(put(URI + user.getId() + "/")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId().toString()));

    }

    @Test
    @WithMockUser(value = "spring")
    public void userControllerTestDeleteUser() throws Exception {
        User userToDelete = userFactory.getObject();
        assert userToDelete != null;
        userToDelete = userRepository.save(userToDelete);

        UserDetails userDetails = UserDetailsImpl.builder().email(userToDelete.getEmail()).build();

        mockMvc.perform(delete(URI + "me/")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User has been deleted"));;
    }

    @Test
    @WithMockUser(value = "spring")
    public void userControllerTestCreateUser() throws Exception {

        User testUser = new UserFactory().getObject();
        assert testUser != null;


        mockMvc.perform(post(URI)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(testUser.getFirstName()))
                .andExpect(jsonPath("$.data.password").doesNotExist())
                .andExpect(jsonPath("$.data.email").doesNotExist());
    }

}