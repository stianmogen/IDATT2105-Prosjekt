package com.controller;

import com.dto.UserDto;
import com.dto.UserRegistrationDto;
import com.model.User;
import com.querydsl.core.types.Predicate;
import com.service.UserService;
import com.utils.Constants;
import com.utils.Response;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/users/")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    @ApiOperation(value = "Gets all users info")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserDto> getAllUsers(@QuerydslPredicate(root = User.class) Predicate predicate,
                                     @PageableDefault(size = Constants.PAGINATION_SIZE, sort="firstName", direction = Sort.Direction.ASC)Pageable pageable){
        log.debug("[X] Request to look up users");
        return this.userService.getAllUsersDto(predicate, pageable);
    }

    @PostMapping
    @ApiOperation(value = "Creates and persists a users info")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto){
        log.debug("[X] Request to save user with email={}", userRegistrationDto.getEmail());
        return userService.saveUser(userRegistrationDto);
    }

    @GetMapping("{id}/")
    @ApiOperation(value = "Gets a specific users info")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserById(@PathVariable UUID id){
        log.debug("[X] Request to look up user with id={}", id);
        return userService.getUserByUUID(id);
    }

    @GetMapping("me/")
    @ApiOperation(value = "Gets a users own info")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(Authentication authentication){
        UserDetails user = (UserDetails) authentication.getPrincipal();

        log.debug("[X] Request to get personal userinfo with token");
        return this.userService.getUserDtoByEmail(user.getUsername());
    }


    @DeleteMapping("me/")
    @Transactional
    @ApiOperation(value = "Deletes a users own info")
    @ResponseStatus(HttpStatus.OK)
    public Response deleteUser(Authentication authentication){
        UserDetails user = (UserDetails) authentication.getPrincipal();
        log.debug("[X] Request to delete User with email={}", user.getUsername());
        userService.deleteUser(user.getUsername());
        return new Response("User has been deleted");
    }


    @PutMapping("{userId}/")
    @ApiOperation(value = "Update a specific users info")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@PathVariable UUID userId, @RequestBody @Valid UserDto user, Authentication authentication){
        log.debug("[X] Request to update user with id={}", userId);
        return this.userService.updateUser(userId, user);
    }

}
