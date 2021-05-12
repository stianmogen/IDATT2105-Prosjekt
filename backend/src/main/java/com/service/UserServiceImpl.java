package com.service;

import com.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserDto getUserDtoByEmail(String email) {
        return null;
    }

    @Override
    public List<UserDto> getAllUsersDto() {
        return null;
    }

    @Override
    public UserDto getUserByUUID(UUID userId) {
        return null;
    }
}
