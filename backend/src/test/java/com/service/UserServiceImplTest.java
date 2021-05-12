package com.service;

import com.dto.UserDto;
import com.dto.UserRegistrationDto;
import com.factories.UserFactory;
import com.model.User;
import com.querydsl.core.types.Predicate;
import com.repository.UserRepository;
import com.utils.ListingUtils;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    ModelMapper modelMapper = new ModelMapper();

    private User user;
    private User secondUser;
    private List<User> usersExpected;
    private UserRegistrationDto userRegistrationDto;
    private Predicate predicate;
    private Pageable pageable;

    @BeforeEach
    void setup() throws Exception {
        userService = new UserServiceImpl(modelMapper, userRepository);
        user = new UserFactory().getObject();
        userRegistrationDto = modelMapper.map(user, UserRegistrationDto.class);
        userRepository.save(user);

        predicate = ListingUtils.getEmptyPredicate();
        pageable = ListingUtils.getDefaultPageable();
    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void testGetUserDtoByEmailReturnsUserDto() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        UserDto userDtoFound = userService.getUserDtoByEmail(user.getEmail());
        assertThat(userDtoFound.getEmail()).isEqualTo(user.getEmail());
    }
}
