package com.security.validator;

import com.exception.InvalidJwtToken;
import com.model.RefreshToken;
import com.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JwtTokenValidator implements TokenValidator {

    private RefreshTokenService refreshTokenService;

    @Override
    public void validate(String jti) {
        RefreshToken refreshToken = refreshTokenService.getByJti(jti);
        if (!refreshToken.isValid())
            throw new InvalidJwtToken();
    }
}

