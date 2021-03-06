package com.security.config;


import com.security.filter.JWTAuthenticationFilter;
import com.security.filter.JWTUsernamePasswordAuthenticationFilter;
import com.service.RefreshTokenService;
import com.service.UserDetailsServiceImpl;
import com.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtConfig jwtConfig;

    private static final String[] DOCS_WHITELIST = {
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**"
    };

    /**
     * Sets up the web security configuration
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().configurationSource(request -> {
            var cors = new CorsConfiguration();
            cors.setAllowedOrigins(List.of("*"));
            cors.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS"));
            cors.setAllowedHeaders(List.of("*"));
            return cors;
        })
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(DOCS_WHITELIST).permitAll()

                .antMatchers(HttpMethod.POST, jwtConfig.getUri() + "/login").permitAll()

                .antMatchers(HttpMethod.POST, "/users/**").permitAll()
                .antMatchers(HttpMethod.GET, "/users/me/").permitAll()
                .antMatchers(HttpMethod.GET, "/users/**").hasAnyRole("USER", "MODERATOR", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/users/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/users/{userId}/").hasAnyRole("ADMIN")

                .antMatchers("/rooms/{roomId}/reservations/").permitAll()

                .antMatchers(HttpMethod.GET, "/buildings/**").hasAnyRole("USER", "MODERATOR", "ADMIN")
                .antMatchers(HttpMethod.POST,"/buildings/**").hasAnyRole("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.PUT,"/buildings/**").hasAnyRole("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.DELETE, "/buildings/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/rooms/**").hasAnyRole("USER", "MODERATOR", "ADMIN")
                .antMatchers(HttpMethod.POST,"/rooms/**").hasAnyRole("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.PUT,"/rooms/**").hasAnyRole("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.DELETE, "/rooms/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/sections/**").hasAnyRole("USER", "MODERATOR", "ADMIN")
                .antMatchers(HttpMethod.POST,"/sections/**").hasAnyRole("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.PUT,"/sections/**").hasAnyRole("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.DELETE, "/sections/**").hasRole("ADMIN")

                .antMatchers("/admin/**").hasRole("ADMIN")

                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(
                        (req, res, e) -> {
                            res.setContentType("application/json");
                            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            res.getOutputStream().println("{ \"message\": \"Brukernavn eller passord er feil\"}");
                        })
                .and()
                .addFilter(new JWTUsernamePasswordAuthenticationFilter(refreshTokenService, authenticationManager(), jwtConfig))
                .addFilterAfter(new JWTAuthenticationFilter(jwtConfig, jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);

        return daoAuthenticationProvider;
    }


    /**
     * This sets up the configuration for Cross-Origin Resource Sharing (CORS)
     *
     * Note:
     * Cors is not a protection agains CSRF attacks
     * Poor cors configuration opens for cross-domain based attacks
     *
     * @return the configuration source
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

}

