package com.integration;


import com.factories.RoomFactory;
import com.factories.SectionFactory;
import com.model.Reservation;
import com.model.Room;
import com.model.Section;
import com.repository.BuildingRepository;
import com.repository.ReservationRepository;
import com.repository.RoomRepository;
import com.repository.SectionRepository;
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

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;


import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RoomSearchIntegrationTest {

      private final String URI = "/activities/";

      @Autowired
      private MockMvc mvc;

      @Autowired
      private BuildingRepository buildingRepository;

      @Autowired
      private RoomRepository roomRepository;

      @Autowired
      private SectionRepository sectionRepository;

      @Autowired
      private ReservationRepository reservationRepository;

      private RoomFactory roomFactory = new RoomFactory();

      private SectionFactory sectionFactory = new SectionFactory();

      private ZonedDateTime dateTime1;

      private ZonedDateTime dateTime2;

      private Room room1;

      private Room room2;


      @BeforeEach
      public void setUp() throws Exception {
            room1 = roomFactory.getObject();
            assert room1 != null;
            room1.setName("ROOM_1");
            room1 = roomRepository.save(room1);

            room2 = roomFactory.getObject();
            assert room2 != null;
            room2.setName("ROOM_2");
            room2 = roomRepository.save(room2);

            Section section1 = sectionFactory.getObject();
            assert section1 != null;
            section1.setRoom(room1);
            section1 = sectionRepository.save(section1);

            Section section2 = sectionFactory.getObject();
            assert section2 != null;
            section2.setRoom(room1);
            section2 = sectionRepository.save(section2);

            Section section3 = sectionFactory.getObject();
            assert section3 != null;
            section3.setRoom(room2);
            section3 = sectionRepository.save(section3);

            Section section4 = sectionFactory.getObject();
            assert section4 != null;
            section4.setRoom(room2);
            section4 = sectionRepository.save(section4);

            ZoneId zoneId = ZoneId.of("UTC+1");

            dateTime1 = ZonedDateTime.of(2020, 1, 1, 12, 0, 0, 0, zoneId);
            dateTime2 = ZonedDateTime.of(2020, 1, 1, 16, 0, 0, 0, zoneId);

            reservationRepository.save(new Reservation(null, List.of(section1, section2), dateTime1, dateTime2, 1, ""));
            reservationRepository.save(new Reservation(null, List.of(section3), dateTime1, dateTime2, 1, ""));

      }

      @AfterEach
      public void cleanUp(){
            roomRepository.deleteAll();
            buildingRepository.deleteAll();
            sectionRepository.deleteAll();
            reservationRepository.deleteAll();
      }

      @Test
      @WithMockUser
      public void testSearchByAvailableStartAndEndTime() throws Exception {
            mvc.perform(get(URI)
                  .accept(MediaType.APPLICATION_JSON)
                  .param("availableAfter", String.valueOf(dateTime1.minusHours(2L)))
                  .param("availableBefore", String.valueOf(dateTime1.minusHours(1L))))
                  .andExpect(status().isOk())
                  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                  .andExpect(jsonPath("$.content.length()").value(2));
      }

      @Test
      @WithMockUser
      public void testSearchByUnavailableStartAndEndTime() throws Exception {
            mvc.perform(get(URI)
                  .accept(MediaType.APPLICATION_JSON)
                  .param("availableAfter", String.valueOf(dateTime1.minusHours(1L)))
                  .param("availableBefore", String.valueOf(dateTime2.plusHours(1L))))
                  .andExpect(status().isOk())
                  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                  .andExpect(jsonPath("$.content.length()").value(1));
      }
}
