package com.controller;

import com.factories.RoomFactory;
import com.factories.SectionFactory;
import com.model.Room;
import com.model.Section;
import com.repository.RoomRepository;
import com.repository.SectionRepository;
import org.aspectj.lang.annotation.Before;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private Section section;

    private Room room;

    @BeforeEach
    void setup() throws Exception {

      section = new SectionFactory().getObject();
      assert section != null;
      section = sectionRepository.save(section);

    }

    @AfterEach
    void cleanup() {
        sectionRepository.deleteAll();
    }


    @Test
    @WithMockUser(value = "spring")
    public void userControllerTestGetSectionById() throws Exception {

        mockMvc.perform(get(URI + section.getId() + "/")
                .contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(section.getName()));

    }


}
