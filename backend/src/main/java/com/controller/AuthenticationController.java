package com.controller;

import com.dto.JwtTokenResponse;
import com.security.config.JwtConfig;
import com.security.service.JwtService;
import com.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
