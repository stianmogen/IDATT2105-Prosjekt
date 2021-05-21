package com.security.service;


import com.model.User;
import com.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class SecurityService {

    UserRepository userRepository;


    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = authentication != null ? (UserDetails) authentication.getPrincipal() : null;
        String email = userDetails != null ? userDetails.getUsername() : "";
        return userRepository.findByEmail(email).orElse(null);
    }

    public boolean isUser(UUID userId) {
        User user = getUser();
        return user != null && user.getId().equals(userId);
    }
}

