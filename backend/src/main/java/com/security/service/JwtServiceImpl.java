package com.security.service;


import com.dto.JwtTokenResponse;
import com.exception.InvalidJwtToken;
import com.exception.RefreshTokenNotFound;
import com.model.User;
import com.repository.UserRepository;
import com.security.UserDetailsImpl;
import com.security.config.JwtConfig;
import com.security.extractor.TokenExtractor;
import com.security.token.JwtAccessToken;
import com.security.token.JwtRefreshToken;
import com.security.token.TokenFactory;
import com.security.validator.TokenValidator;
import com.service.RefreshTokenService;
import com.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@AllArgsConstructor
public class JwtServiceImpl implements JwtService {

    private JwtUtil jwtUtil;
    private JwtConfig jwtConfig;
    private TokenFactory tokenFactory;
    private TokenExtractor tokenExtractor;
    private UserRepository userRepository;
    private TokenValidator tokenValidator;
    private RefreshTokenService refreshTokenService;

    /**
     * Create a new jwt access token from the refresh token in the request header.
     *
     * @return the new jwt access token
     */
    @Override
    public JwtTokenResponse refreshToken(String header) {
        JwtRefreshToken currentJwtRefreshToken = getCurrentJwtRefreshToken(header);
        doValidateToken(currentJwtRefreshToken);

        User user = getUserFromToken(currentJwtRefreshToken);
        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();

        JwtAccessToken accessToken = tokenFactory.createAccessToken(userDetails);
        JwtRefreshToken refreshToken = (JwtRefreshToken) tokenFactory.createRefreshToken(userDetails);

        refreshTokenService.rotateRefreshToken(currentJwtRefreshToken, refreshToken);

        return new JwtTokenResponse(accessToken.getToken(), refreshToken.getToken());
    }

    private JwtRefreshToken getCurrentJwtRefreshToken(String header) {
        String token = tokenExtractor.extract(header);

        return jwtUtil.parseToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token claims."));
    }

    private User getUserFromToken(JwtRefreshToken refreshToken) {
        String subject = refreshToken.getSubject();
        return userRepository.findByEmail(subject)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));
    }

    private void doValidateToken(JwtRefreshToken refreshToken) {
        try {
            tokenValidator.validate(refreshToken.getJti());
        } catch (InvalidJwtToken | RefreshTokenNotFound ex) {
            log.error("[X] Token validation failed.", ex);
            refreshTokenService.invalidateSubsequentTokens(refreshToken.getJti());
            throw new BadCredentialsException("Invalid refresh token", ex);
        }
    }
}

