package com.security.token;


import com.security.UserDetailsImpl;

public interface TokenFactory {
    JwtAccessToken createAccessToken(UserDetailsImpl userDetails);

    JwtToken createRefreshToken(UserDetailsImpl userDetails);
}

