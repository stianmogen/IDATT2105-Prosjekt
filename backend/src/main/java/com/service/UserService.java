package com.service;

import com.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {
    UserDto getUserDtoByEmail(String email);
    List<UserDto> getAllUsersDto();
    UserDto getUserByUUID(UUID userId);
}
