package com.factories;

import com.model.Reservation;
import com.model.Section;
import com.model.User;
import org.springframework.beans.factory.FactoryBean;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class ReservationFactory implements FactoryBean<Reservation> {

    public Reservation getObjectWithUserAndSection(User user, Section section) throws Exception {
        return Reservation.builder()
                .id(UUID.randomUUID())
                .createdAt(ZonedDateTime.now())
                .startTime(ZonedDateTime.now())
                .endTime(ZonedDateTime.now())
                .user(user)
                .sections(List.of(section))
                .build();
    }

    @Override
    public Reservation getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
