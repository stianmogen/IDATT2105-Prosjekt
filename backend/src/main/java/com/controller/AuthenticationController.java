package com.controller;

import com.dto.JwtTokenResponse;
import com.security.config.JwtConfig;
import com.security.service.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("auth/")
@AllArgsConstructor
public class AuthenticationController {

    private JwtConfig jwtConfig;
    private JwtService jwtService;

    @GetMapping("/refresh-token/")
    public JwtTokenResponse refreshToken(HttpServletRequest request) {
        String header = request.getHeader(jwtConfig.getHeader());
        return jwtService.refreshToken(header);
    }
}
