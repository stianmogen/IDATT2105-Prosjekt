package com.controller;

import com.dto.UserRegistrationDto;
import com.factories.UserFactory;
import com.model.User;
import com.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;

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

    @BeforeEach
    public void setUp() throws Exception {
        user = new UserFactory().getObject();
        assert user != null;

        userRepository.save(user);
    }

    @AfterEach
    public void cleanUp(){
        userRepository.deleteAll();
    }

    @Test
    public void testGetUserById(){

    }
}