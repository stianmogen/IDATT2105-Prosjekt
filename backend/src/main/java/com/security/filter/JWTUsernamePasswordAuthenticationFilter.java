package com.security.filter;

import com.controller.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.security.UserDetailsImpl;
import com.security.config.JwtConfig;
import com.security.token.JwtToken;
import com.security.token.JwtTokenFactory;
import com.security.token.TokenFactory;
import com.service.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Filter to check the existence and validity of the access token on the Authorization header.
 */
@Slf4j
public class JWTUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private RefreshTokenService refreshTokenService;
    private AuthenticationManager authenticationManager;
    private TokenFactory tokenFactory;
    private ObjectMapper objectMapper;
    private JwtConfig jwtConfig;

    public JWTUsernamePasswordAuthenticationFilter(RefreshTokenService refreshTokenService, AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.tokenFactory = new JwtTokenFactory(jwtConfig);
        this.objectMapper = new ObjectMapper();

        this.setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher(jwtConfig.getUri() + "/login", "POST"));
    }

    /**
     * Attempt to Authenticate the request.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            LoginRequest credentials = new ObjectMapper().
                    readValue(request.getInputStream(), LoginRequest.class);

            log.debug("Found credentials, authenticating user: {}", credentials);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    credentials.getEmail(),
                    credentials.getPassword(),
                    new ArrayList<>());

            return authenticationManager.authenticate(authentication);
        } catch (IOException exception) {
            log.error("Exception occurred while authenticating user", exception);
            throw new RuntimeException(exception);
        }
    }

    /**
     * Create JWT access and refresh token  and write it to response header upon successful authentication.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException {
        UserDetailsImpl userDetails = ((UserDetailsImpl) auth.getPrincipal());

        JwtToken accessToken = tokenFactory.createAccessToken(userDetails);
        JwtToken refreshToken = tokenFactory.createRefreshToken(userDetails);
        refreshTokenService.saveRefreshToken(refreshToken);

        log.debug("Successfully created access and refresh token for user (email:{})", userDetails.getUsername());

        Map<String, String> tokens = new HashMap<>();
        tokens.put("token", accessToken.getToken());
        tokens.put("refreshToken", refreshToken.getToken());

        response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + accessToken);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), tokens);

        log.debug("Successfully authenticated user. (email:{})", userDetails.getUsername());
    }

}
