package com.security.service;


import com.dto.JwtTokenResponse;

public interface JwtService {

    JwtTokenResponse refreshToken(String header);

}

