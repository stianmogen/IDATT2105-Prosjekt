package com.service;

import com.dto.SectionResponseDto;
import com.factories.SectionFactory;
import com.model.Section;
import com.querydsl.core.types.Predicate;
import com.repository.RoomRepository;
import com.repository.SectionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SectionServiceImplTest {

    @InjectMocks
    SectionServiceImpl sectionService;

    @Mock
    RoomRepository roomRepository;

    @Mock
    SectionRepository sectionRepository;

    ModelMapper modelMapper = new ModelMapper();

    private Section section;
    private Section section2;

    private Predicate predicate;
    private Pageable pageable;
    List<Section> sectionList;

    @BeforeEach
    void setUp() throws Exception {
        section = new SectionFactory().getObject();
        section2 = new SectionFactory().getObject();
        assert section != null;
        assert section2 != null;
        sectionRepository.save(section);
        sectionRepository.save(section2);
    }

    @AfterEach
    void cleanUp(){
        sectionRepository.deleteAll();
    }

    @Test
    void testGetSectionById(){
        when(sectionRepository.findById(section.getId())).thenReturn(Optional.of(section));
        SectionResponseDto sectionFound = sectionService.getSectionById(section.getId());
        assertThat(sectionFound.getId()).isEqualTo(section.getId());
        assertThat(sectionFound.getId()).isNotNull();
    }

    @Test
    void testGetSectionByRoomId(){
        when(sectionRepository.findAllByRoomId(section2.getRoom().getId())).thenReturn(List.of(section2));
        List<Section> sectionsFound = sectionService.getSectionByRoomId(section2.getRoom().getId());
        assertThat(sectionsFound.stream().findFirst().get().getId()).isEqualTo(section2.getId());
        assertThat(sectionsFound.stream().findFirst().get().getId()).isNotNull();
    }



}
