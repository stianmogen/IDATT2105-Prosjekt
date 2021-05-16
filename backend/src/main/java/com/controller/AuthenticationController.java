package com.controller;

import com.dto.JwtTokenResponse;
import com.security.config.JwtConfig;
import com.security.service.JwtService;
import com.service.UserService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("auth/")
@AllArgsConstructor
public class AuthenticationController {

    private JwtConfig jwtConfig;
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @GetMapping("/refresh-token/")
    public JwtTokenResponse refreshToken(HttpServletRequest request) {
        String header = request.getHeader(jwtConfig.getHeader());
        return jwtService.refreshToken(header);
    }

    /*@PostMapping("/change-password/")
    @ResponseStatus(HttpStatus.OK)
    public Response updatePassword(Principal principal, @RequestBody @Valid UserPasswordUpdateDto user) {
        userService.changePassword(principal, user);
        return new Response("Password was successfully changed");
    }

    *//**
     * Creates a reset password token and sends email if correct email i provided
     *
     * @param UserPasswordForgotDto of the users which should have its password reset
     * @return
     *//*
    @SneakyThrows
    @PostMapping("/forgot-password/")
    @ResponseStatus(HttpStatus.OK)
    public Response forgotPassword(@RequestBody UserPasswordForgotDto UserPasswordForgotDto) {
        userService.forgotPassword(UserPasswordForgotDto.getEmail());

        log.info("Email sent to {} for resetting password", UserPasswordForgotDto.getEmail());
        return new Response("An email for resetting password has been sent!");
    }

    *//**
     * Reset the given users password, if a valid token is provided.
     *
     * @param userPasswordResetDto userPasswordResetDto DTO the new passord the user want to change, the email of the user and the PasswordResetToken.
     * @throws ResponseStatusException if the token or linked email is invalid.
     **//*
    @PostMapping("/reset-password/{passwordResetTokenId}/")
    @ResponseStatus(HttpStatus.OK)
    public Response resetPassword(@RequestBody @Valid UserPasswordResetDto userPasswordResetDto, @PathVariable UUID passwordResetTokenId) {
        userService.validateResetPassword(userPasswordResetDto, passwordResetTokenId);
        log.info("Password was changed for user {}", userPasswordResetDto.getEmail());
        return new Response("Password was changed successfully");
    }*/

}
