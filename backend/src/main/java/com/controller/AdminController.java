package com.controller;

import com.dto.UserDto;
import com.dto.UserUpdateRoleDto;
import com.service.AdminService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("admin/")
@AllArgsConstructor
public class AdminController
{
      @Autowired
      private final AdminService adminService;

      @PutMapping
      @ApiOperation(value = "Change user roles")
      @ResponseStatus(HttpStatus.OK)
      public UserDto getAllUsers(@RequestBody UserUpdateRoleDto updateRoleDto){
            log.debug("[X] Request to change role for user ={}", updateRoleDto.getEmail());
            return this.adminService.updateUserRoles(updateRoleDto);
      }
}
