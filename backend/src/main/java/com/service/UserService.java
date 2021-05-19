package com.service;

import com.dto.UserDto;
import com.dto.UserRegistrationDto;
import com.model.User;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {
    UserDto getUserDtoByEmail(String email);

    Page<UserDto> getAllUsersDto(Predicate predicate, Pageable pageable);

    UserDto getUserByUUID(UUID userId);

    UserDto saveUser(UserRegistrationDto userRegistrationDto);

    UserDto deleteUser(String username);

    public UserDto updateUser(UUID id, UserDto user);
}
