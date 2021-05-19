package com.service;

import com.dto.BuildingDto;
import com.dto.BuildingResponseDto;
import com.factories.BuildingFactory;
import com.model.Building;
import com.repository.BuildingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import static com.utils.StringRandomizer.getRandomString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;




@ExtendWith(MockitoExtension.class)
public class BuildingServiceImplTest {

    @InjectMocks
   private BuildingServiceImpl buildingService;

    @Mock
   private BuildingRepository buildingRepository;

    ModelMapper modelMapper = new ModelMapper();

    private Building building;
    private List<Building> buildingsExpected;

    @BeforeEach
    void setUp() throws Exception {
        building = new BuildingFactory().getObject();
        lenient().when(buildingRepository.save(building)).thenReturn(building);
    }

    @Test
    void testBuildingServiceImplGetBuilding() {
        when(buildingRepository.findById(building.getId())).thenReturn(Optional.of(building));
        BuildingResponseDto buildingFound = buildingService.getBuildingById(building.getId());

        assertThat(buildingFound.getId()).isEqualTo(building.getId());
    }

    @Test
    void testBuildingServiceImplDeleteBuilding() throws Exception {


    }


    @Test
    void testBuildingServiceImplGetAllBuildings() {
        buildingsExpected = List.of(building);

        when(buildingRepository.findAll()).thenReturn(buildingsExpected);
        List<Building> buildingsFound = buildingRepository.findAll();

        Assertions.assertIterableEquals(buildingsExpected, buildingsFound);
    }

    @Test
    void testBuildingServiceImplUpdateBuilding() {
        when(buildingRepository.findById(building.getId())).thenReturn(Optional.ofNullable(building));
        String oldName= building.getName();
        building.setName(getRandomString(10));

        lenient().when(buildingRepository.save(any())).thenReturn(building);

        BuildingResponseDto updatedBuilding = buildingService.updateBuilding(building.getId(), modelMapper.map(building, BuildingDto.class));

        assertThat(building.getId()).isEqualTo(updatedBuilding.getId());
        assertThat(oldName).isNotEqualTo(updatedBuilding.getName());
    }

}
