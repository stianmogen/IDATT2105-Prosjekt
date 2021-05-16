package com.service;


import com.model.RefreshToken;
import com.security.token.JwtRefreshToken;
import com.security.token.JwtToken;

public interface RefreshTokenService {

    RefreshToken saveRefreshToken(JwtToken token);

    RefreshToken getByJti(String jti);

    void invalidateSubsequentTokens(String jti);

    void rotateRefreshToken(JwtRefreshToken oldRefreshToken, JwtRefreshToken newRefreshToken);
}
