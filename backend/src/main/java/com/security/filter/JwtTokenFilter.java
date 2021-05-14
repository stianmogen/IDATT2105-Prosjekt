package com.security.filter;
/*
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    //private JWTConfig jwtConfig;
    //private JwtUtil jwtUtil;



    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        String header = extractAuthorizationHeaderFromRequest(httpServletRequest);
        log.debug("Filtering Jwt from request header: {}", header);

        /*if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        //processAuthentication(httpServletRequest);
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    private String extractAuthorizationHeaderFromRequest(HttpServletRequest request) {
        //return request.getHeader(jwtConfig.getHeader());
        return null;
    }

}*/
