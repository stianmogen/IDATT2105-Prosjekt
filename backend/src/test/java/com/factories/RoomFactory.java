package com.factories;

import com.model.Room;
import org.springframework.beans.factory.FactoryBean;

import java.util.UUID;

import static com.utils.StringRandomizer.getRandomString;

public class RoomFactory implements FactoryBean<Room> {
    @Override
    public Room getObject() throws Exception {
        return Room.builder()
                .id(UUID.randomUUID())
                .capacity(10)
                .name(getRandomString(10))
                .level(1)
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
