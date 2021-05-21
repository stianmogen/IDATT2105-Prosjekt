package com.service;

import com.dto.UserDto;
import com.dto.UserRegistrationDto;
import com.factories.UserFactory;
import com.model.User;
import com.querydsl.core.types.Predicate;
import com.repository.ReservationRepository;
import com.repository.UserRepository;
import com.utils.ListingUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static com.utils.StringRandomizer.getRandomString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    BCryptPasswordEncoder encoder;

    @Mock
    private UserRepository userRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Mock
    private ReservationRepository reservationRepository;

    private User user;
    private User secondUser;
    private List<User> usersExpected;
    private UserRegistrationDto userRegistrationDto;
    private Predicate predicate;
    private Pageable pageable;


    @BeforeEach
    void setUp() throws Exception {
        userService = new UserServiceImpl(modelMapper, userRepository, reservationRepository, encoder);
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
    void testUserServiceImplGetUserDtoByEmailReturnsUserDto() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        UserDto userDtoFound = userService.getUserDtoByEmail(user.getEmail());
        assertThat(userDtoFound.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void testUserServiceImplDeleteUserDeletesUser() throws Exception {
        secondUser = new UserFactory().getObject();
        userRepository.save(secondUser);
        usersExpected = List.of(user, secondUser);
        when(userRepository.findAll(any(Predicate.class), any(PageRequest.class))).thenReturn(new PageImpl<>(usersExpected, pageable, usersExpected.size()));
        Page<UserDto> usersFound = userService.getAllUsersDto(predicate, pageable);

        assertThat(usersFound.stream().findFirst().get().getEmail()).
                isEqualTo(usersExpected.get(0).getEmail());
        assertThat(usersFound.getTotalElements())
                .isEqualTo(Long.valueOf(usersExpected.size()));

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(user.getId());
        doNothing().when(reservationRepository).deleteReservationByUser(user);
        userService.deleteUser(user.getEmail());

        List<User> usersExpectedAfterDelete = List.of(secondUser);
        when(userRepository.findAll(any(Predicate.class), any(PageRequest.class))).thenReturn(new PageImpl<>(usersExpectedAfterDelete, pageable, usersExpected.size() - 1));
        Page<User> usersFound2 = userRepository.findAll(predicate, pageable);

        assertThat(usersFound2.getTotalElements())
                .isEqualTo(Long.valueOf(usersExpected.size() - 1));
        assertThat(usersFound2.stream().findFirst().get().getEmail()).
                isEqualTo(usersExpected.get(1).getEmail());
    }

    @Test
    void testUserServiceImplGetUserById() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        UserDto userFound = userService.getUserByUUID(user.getId());

        assertThat(userFound.getId()).isEqualTo(user.getId());

    }

    @Test
    void testUserServiceImplGetAllUsers() {
        usersExpected = List.of(user);

        when(userRepository.findAll()).thenReturn(usersExpected);
        List<User> usersFound = userRepository.findAll();

        Assertions.assertIterableEquals(usersExpected, usersFound);

    }

    @Test
    void testUserServiceImplUpdateUser() {

        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        String oldSurename = user.getSurname();
        user.setSurname(getRandomString(10));

        lenient().when(userRepository.save(any())).thenReturn(user);

        UserDto updateUser = userService.updateUser(user.getId(), modelMapper.map(user, UserDto.class));

        assertThat(user.getId()).isEqualTo(updateUser.getId());
        assertThat(oldSurename).isNotEqualTo(updateUser.getSurname());

    }


}
