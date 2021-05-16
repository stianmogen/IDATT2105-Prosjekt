package com.factories;

import com.model.Section;
import org.springframework.beans.factory.FactoryBean;

import java.util.UUID;

import static com.utils.StringRandomizer.getRandomString;

public class SectionFactory implements FactoryBean<Section> {
    @Override
    public Section getObject() throws Exception {
        return Section.builder()
                .id(UUID.randomUUID())
                .room(new RoomFactory().getObject())
                .capacity(1)
                .name(getRandomString(5))
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
