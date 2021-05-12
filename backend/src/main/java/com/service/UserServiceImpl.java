package com.service;

import com.dto.UserDto;
import com.dto.UserRegistrationDto;
import com.exception.EmailInUseException;
import com.exception.UserNotFoundException;
import com.model.User;
import com.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
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
    public Page<UserDto> getAllUsersDto(Predicate predicate, Pageable pageable) {
        return userRepository.findAll(predicate, pageable).map(user -> modelMapper.map(user, UserDto.class));
    }

    @Override
    public User getUserByUUID(UUID userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserDto saveUser(UserRegistrationDto userRegistrationDto){
        if (emailExist(userRegistrationDto.getEmail()))
            throw new EmailInUseException();
        User user = modelMapper.map(userRegistrationDto, User.class);
        //TODO Password Encryption and Encoding
        user.setPassword(userRegistrationDto.getPassword());
        return modelMapper.map(userRepository.save(user), UserDto.class);

    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
