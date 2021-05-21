package com.service;

import com.dto.UserDto;
import com.dto.UserUpdateRoleDto;

public interface AdminService {
      UserDto updateUserRoles(UserUpdateRoleDto updateRoleDto);
}
