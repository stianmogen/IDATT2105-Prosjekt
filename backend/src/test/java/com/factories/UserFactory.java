package com.factories;

import com.model.User;
import org.springframework.beans.factory.FactoryBean;

import java.util.UUID;
import static com.utils.StringRandomizer.getRandomEmail;
import static com.utils.StringRandomizer.getRandomString;


public class UserFactory implements FactoryBean<User> {

    @Override
    public User getObject() throws Exception {
        return User.builder()
                .id(UUID.randomUUID())
                .email(getRandomEmail())
                .firstName(getRandomString(5))
                .surname(getRandomString(5)+"@email.com")
                .password(getRandomString(3)+"FactoryPassword123")
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
