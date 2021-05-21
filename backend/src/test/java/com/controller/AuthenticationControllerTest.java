package com.controller;

import com.factories.UserFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.model.RefreshToken;
import com.model.User;
import com.repository.RefreshTokenRepository;
import com.repository.UserRepository;
import com.security.config.JwtConfig;
import com.security.token.JwtRefreshToken;
import com.security.token.TokenFactory;
import com.utils.JwtUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest(webEnvironment = MOCK)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthenticationControllerTest {

    private static final String URI = "/auth/";
    private static final String password = "password123";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;


    @Autowired
    private TokenFactory tokenFactory;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtConfig jwtConfig;

    private User user;

    private RefreshToken refreshToken;

    private String rawRefreshToken;

    private String rawAccessToken;

    @BeforeEach
    void setUp() throws Exception {
        user = new UserFactory().getObject();
        user.setPassword(encoder.encode(password));

        user = userRepository.save(user);

        LoginRequest loginRequest = new LoginRequest(user.getEmail(), password);
        String loginJson = objectMapper.writeValueAsString(loginRequest);

        MvcResult mvcResult = mvc.perform(post(URI + "login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andReturn();

        rawAccessToken = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.token");
        rawRefreshToken = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.refreshToken");
        JwtRefreshToken jwtRefreshToken = jwtUtil.parseToken(this.rawRefreshToken)
                .get();
        refreshToken = RefreshToken.builder()
                .jti(UUID.fromString(jwtRefreshToken.getJti()))
                .isValid(true)
                .build();
        refreshTokenRepository.save(refreshToken);

    }

    @AfterEach
    void cleanup() {
    }

    /**
     * Test that a new access token is returned when authorizing with a valid refresh token.
     */
    @Test
    void testRefreshTokenWithValidRefreshTokenReturnsNewToken() throws Exception {
        MvcResult mvcResult = mvc.perform(get(URI + "refresh-token/")
                .header(jwtConfig.getHeader(), jwtConfig.getPrefix() + rawRefreshToken))
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty())
                .andReturn();

        String newToken = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.token");
        String actualEmail = jwtUtil.getEmailFromToken(newToken);

        assertThat(actualEmail).isEqualTo(user.getEmail());
    }

    /**
     * Test that reusing an old refresh token invalidates the chain of refresh tokens.
     */
    @Test
    void testRefreshTokenWithReusedRefreshTokenInvalidatesSubsequentTokens() throws Exception {
        MvcResult mvcResult = mvc.perform(get(URI + "refresh-token/")
                .header(jwtConfig.getHeader(),
                        jwtConfig.getPrefix() + rawRefreshToken))
                .andReturn();

        String newRawRefreshToken = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.refreshToken");

        mvc.perform(get(URI + "refresh-token/")
                .header(jwtConfig.getHeader(),
                        jwtConfig.getPrefix() + newRawRefreshToken));

        JwtRefreshToken jwtRefreshToken = jwtUtil.parseToken(newRawRefreshToken)
                .get();
        RefreshToken oldRefreshToken = refreshTokenRepository.findById(refreshToken.getJti())
                .get();
        RefreshToken newRefreshToken = refreshTokenRepository.findById(UUID.fromString(jwtRefreshToken.getJti()))
                .get();

        assertThat(oldRefreshToken.isValid()).isFalse();
        assertThat(newRefreshToken.isValid()).isFalse();
    }

    }




