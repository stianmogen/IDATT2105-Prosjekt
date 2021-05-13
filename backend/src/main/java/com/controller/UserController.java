package com.controller;

import com.dto.UserDto;
import com.dto.UserRegistrationDto;
import com.model.User;
import com.querydsl.core.types.Predicate;
import com.service.UserService;
import com.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserDto> getAllUsers(@QuerydslPredicate(root = User.class) Predicate predicate,
                                     @PageableDefault(size = Constants.PAGINATION_SIZE, sort="firstName", direction = Sort.Direction.ASC)Pageable pageable){
        log.debug("[X] Request to look up users");
        return this.userService.getAllUsersDto(predicate, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserRegistrationDto userRegistrationDto){
        log.debug("[X] Request to save user with email={}", userRegistrationDto.getEmail());
        return userService.saveUser(userRegistrationDto);
    }
}
