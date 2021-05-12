package com.service;

import com.dto.UserDto;
import com.exception.UserNotFoundException;
import com.model.User;
import com.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto getUserDtoByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return modelMapper.map(user, UserDto.class);
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
