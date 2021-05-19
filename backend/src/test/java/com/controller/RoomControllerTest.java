package com.controller;

import com.factories.SectionFactory;
import com.factories.UserFactory;
import com.model.Building;
import com.model.Room;
import com.model.Section;
import com.model.User;
import com.repository.BuildingRepository;
import com.repository.RoomRepository;
import com.repository.SectionRepository;
import com.repository.UserRepository;
import com.service.RoomService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

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
    private SectionRepository sectionRepository;

    private User user;
    private Section section;
    private Building building;
    private Room room;


    @BeforeEach
    public void setUp() throws Exception {
        /*
        user = new UserFactory().getObject();
        section = new SectionFactory().getObject();
        assert section != null;
        room = section.getRoom();
        building = room.getBuilding();
        //userRepository.save(user);
        buildingRepository.save(building);
        roomRepository.save(room);
        sectionRepository.save(section);
        */

    }

    @AfterEach
    public void cleanUp(){
        /*
        sectionRepository.deleteAll();
        roomRepository.deleteAll();
        buildingRepository.deleteAll();
        userRepository.deleteAll();
        */
    }

    @Test
    void test(){

    }
}
