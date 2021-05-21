package com.service;

import com.dto.UserDto;
import com.dto.UserUpdateRoleDto;
import com.exception.UserNotFoundException;
import com.model.Role;
import com.model.User;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
      private ModelMapper modelMapper = new ModelMapper();

      @Autowired
      private UserRepository userRepository;

      @Autowired
      private RoleRepository roleRepository;

      @Override
      public UserDto updateUserRoles(UserUpdateRoleDto updateRoleDto) {
            User user = userRepository.findByEmail(updateRoleDto.getEmail()).orElseThrow(UserNotFoundException::new);
            List<Role> roles = roleRepository.findAll();
            Collection<Role> newRoles = new ArrayList<>();
            roles.forEach(role -> {
                  if (role.getName().name().equals(updateRoleDto.getRole())) {
                        newRoles.add(role);
                  }
            });
            user.setRoles(newRoles);
            return modelMapper.map(userRepository.save(user), UserDto.class);
      }
}
