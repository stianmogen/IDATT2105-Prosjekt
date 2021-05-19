package com.service;

import com.dto.RoomDto;
import com.dto.RoomResponseDto;
import com.factories.BuildingFactory;
import com.factories.RoomFactory;
import com.factories.SectionFactory;
import com.model.Room;
import com.model.Section;
import com.querydsl.core.types.Predicate;
import com.repository.RoomRepository;
import com.repository.SectionRepository;
import com.utils.ListingUtils;
import com.utils.StringRandomizer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceImplTest {

    @InjectMocks
    RoomServiceImpl roomService;

    @Mock
    RoomRepository roomRepository;

    @Mock
    SectionRepository sectionRepository;

    ModelMapper modelMapper = new ModelMapper();

    private Section section1;

    private Room room1;

    private Predicate predicate;
    private Pageable pageable;

    ZonedDateTime before;
    ZonedDateTime after;

    @BeforeEach
    void setUp() throws Exception {

        before = ZonedDateTime.now();
        after = ZonedDateTime.now().plusDays(1);

        section1 = new SectionFactory().getObject();
        assert section1 != null;
        room1 = section1.getRoom();
        assert room1 != null;
        sectionRepository.save(section1);
        roomRepository.save(room1);



        predicate = ListingUtils.getEmptyPredicate();
        pageable = ListingUtils.getDefaultPageable();
    }

    @AfterEach
    void cleanUp(){
        sectionRepository.deleteAll();
        roomRepository.deleteAll();
    }

    @Test
    void testFindAvailableRoomsByParticipantsAndDateReturns(){
        when(roomRepository.findAvailableRoom(before, after, section1.getCapacity())).thenReturn(List.of(room1));
        List<RoomResponseDto> roomsFound = roomService.findAvailableRoomsByParticipantsAndDate(predicate, pageable, before, after, section1.getCapacity());
        assertThat(roomsFound.stream().findFirst().get().getId()).isEqualTo(room1.getId());
    }

    @Test
    void testUpdateRoomUpdatesRoomAndReturns() throws Exception {
        when(roomRepository.findById(room1.getId())).thenReturn(Optional.of(room1));
        room1.setName(StringRandomizer.getRandomString(15));
        Section sectionNew = new SectionFactory().getObject();
        sectionNew.setRoom(room1);
        room1.setSections(List.of(sectionNew));

        RoomResponseDto roomUpdated = roomService.updateRoom(room1.getId(), modelMapper.map(room1, RoomDto.class));

        assertThat(room1.getId()).isEqualTo(roomUpdated.getId());
        assertThat(room1.getSections().stream().findFirst().get().getId()).isEqualTo(roomUpdated.getSections().stream().findFirst().get().getId());
        assertThat(room1.getCapacity()).isEqualTo(roomUpdated.getCapacity());
        assertThat(room1.getLevel()).isEqualTo(roomUpdated.getLevel());
        assertThat(roomUpdated).isNotNull();
    }
}
