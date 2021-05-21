package com.factories;

import com.model.Building;
import com.utils.StringRandomizer;
import org.springframework.beans.factory.FactoryBean;

import java.util.UUID;

public class BuildingFactory implements FactoryBean<Building> {
    @Override
    public Building getObject() throws Exception {
        return Building.builder()
                .id(UUID.randomUUID())
                .name(StringRandomizer.getRandomString(3))
                .address(StringRandomizer.getRandomString(10))
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
