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
import com.utils.StringRandomizer;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@SpringBootTest(webEnvironment = MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RoomSearchIntegrationTest {

      private final String URI = "/rooms/";

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

      private ZonedDateTime startTime;

      private ZonedDateTime endTime;

      private Room room1;

      private Room room2;

      private Room room3;

      private Section section1;

      private Section section2;

      private Section section3;


      @BeforeEach
      public void setUp() throws Exception {
            room1 = roomFactory.getObject();
            assert room1 != null;
            room1.setName("ROOM_1");
            buildingRepository.save(room1.getBuilding());
            room1 = roomRepository.save(room1);

            room2 = roomFactory.getObject();
            assert room2 != null;
            room2.setName("ROOM_2");
            buildingRepository.save(room2.getBuilding());
            room2 = roomRepository.save(room2);

            room3 = roomFactory.getObject();
            assert room3 != null;
            room3.setName("ROOM_3");
            buildingRepository.save(room3.getBuilding());
            room3 = roomRepository.save(room3);

            section1 = sectionFactory.getObject();
            assert section1 != null;
            section1.setRoom(room1);
            section1.setCapacity(1);
            section1 = sectionRepository.save(section1);

            section2 = sectionFactory.getObject();
            assert section2 != null;
            section2.setRoom(room1);
            section2.setCapacity(1);
            section2 = sectionRepository.save(section2);

            section3 = sectionFactory.getObject();
            assert section3 != null;
            section3.setRoom(room2);
            section3.setCapacity(2);
            section3 = sectionRepository.save(section3);

            Section section4 = sectionFactory.getObject();
            assert section4 != null;
            section4.setRoom(room2);
            section4.setCapacity(2);
            section4 = sectionRepository.save(section4);

            Section section5 = sectionFactory.getObject();
            assert section5 != null;
            section5.setRoom(room3);
            section5.setCapacity(5);
            section5 = sectionRepository.save(section5);

            ZoneId zoneId = ZoneId.of("UTC+1");

            startTime = ZonedDateTime.of(2020, 1, 1, 12, 0, 0, 0, zoneId);
            endTime = ZonedDateTime.of(2020, 1, 1, 16, 0, 0, 0, zoneId);

            reservationRepository.save(new Reservation(null, List.of(section1, section2), startTime, endTime, 1, ""));
            reservationRepository.save(new Reservation(null, List.of(section3), startTime, endTime, 1, ""));

      }

      @AfterEach
      public void cleanUp(){
            reservationRepository.deleteAll();
            sectionRepository.deleteAll();
            roomRepository.deleteAll();
            buildingRepository.deleteAll();
      }

      @Test
      @WithMockUser
      public void testSearchByAvailableStartAndEndTime() throws Exception {
            mvc.perform(get(URI)
                  .accept(MediaType.APPLICATION_JSON)
                  .param("time", String.valueOf(startTime.minusHours(2L)))
                  .param("time", String.valueOf(startTime.minusHours(1L))))
                  .andExpect(status().isOk())
                  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                  .andExpect(jsonPath("$.content.length()").value(3));
      }

      @Test
      @WithMockUser
      public void testSearchByUnavailableStartAndEndTime() throws Exception {
            mvc.perform(get(URI)
                  .accept(MediaType.APPLICATION_JSON)
                  .param("time", String.valueOf(startTime.minusHours(2L)))
                  .param("time", String.valueOf(endTime.minusHours(1L))))
                  .andExpect(status().isOk())
                  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                  .andExpect(jsonPath("$.content.length()").value(2));
      }

      @Test
      @WithMockUser
      public void testSearchRoomName() throws Exception {
            mvc.perform(get(URI)
                  .accept(MediaType.APPLICATION_JSON)
                  .param("search", room1.getName()))
                  .andExpect(status().isOk())
                  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                  .andExpect(jsonPath("$.content.[0].name").value(room1.getName()))
                  .andExpect(jsonPath("$.content.length()").value(1));
      }

      @Test
      @WithMockUser
      public void testSearchByNonExistingRoomName() throws Exception {
            mvc.perform(get(URI)
                  .accept(MediaType.APPLICATION_JSON)
                  .param("search", StringRandomizer.getRandomString(64)))
                  .andExpect(status().isOk())
                  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                  .andExpect(jsonPath("$.content.length()").value(0));
      }

      @Test
      @WithMockUser
      public void testSearchRoomNames() throws Exception {
            mvc.perform(get(URI)
                  .accept(MediaType.APPLICATION_JSON)
                  .param("search", (room1.getName()) + " " + room2.getName()))
                  .andExpect(status().isOk())
                  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                  .andExpect(jsonPath("$.content.length()").value(2));
      }

      @Test
      @WithMockUser
      void testGetAllWithFilteringReturnsFilteredRoomsOne() throws Exception {
            mvc.perform(get(URI)
                  .accept(MediaType.APPLICATION_JSON)
                  .param("time", String.valueOf(startTime.minusHours(2L)))
                  .param("time", String.valueOf(endTime.minusHours(1L)))
                  .param("capacity", String.valueOf(5)))
                  .andExpect(status().isOk())
                  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                  .andExpect(jsonPath("$.content.length()").value(1));
      }

      @Test
      @WithMockUser
      void testGetSectionForRoomFiltersSection() throws Exception {
            mvc.perform(get(URI+"/"+room1.getId()+"/sections/")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.content.length()").value(2))
                    .andExpect(jsonPath("$.content.[0].id").value(section1.getId().toString()))
                    .andExpect(jsonPath("$.content.[1].id").value(section2.getId().toString()));
      }
      
      @Test
      @WithMockUser
      void testGetSectionsForRoomFiltersSectionWithReservationNoSections() throws Exception {
            mvc.perform(get(URI+"/"+room1.getId()+"/sections/")
                    .accept(MediaType.APPLICATION_JSON)
                    .param("time", String.valueOf(startTime.minusHours(2L)))
                    .param("time", String.valueOf(endTime.plusHours(1L))))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.content.length()").value(0));
      }

      @Test
      @WithMockUser
      void testGetSectionsForRoomFiltersSectionWithReservationTwoSections() throws Exception {
            mvc.perform(get(URI+"/"+room1.getId()+"/sections/")
                    .accept(MediaType.APPLICATION_JSON)
                    .param("time", String.valueOf(startTime.minusHours(100L)))
                    .param("time", String.valueOf(endTime.minusHours(99L))))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.content.length()").value(2));
      }
}
