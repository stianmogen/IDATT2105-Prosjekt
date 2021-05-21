package com.utils;

import com.model.ERole;
import com.model.Role;
import com.model.User;
import com.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;


@Component
public class RoleUtil {

    @Autowired
    private RoleRepository roleRepository;

    public User setRoleToAdmin(User user) {
        Role adminRole = createRoleIfNotFound(ERole.ROLE_ADMIN);
        user.setRoles(Collections.singletonList(adminRole));
        return user;
    }

    public User setRoleToUser(User user) {
        Role userRole = createRoleIfNotFound(ERole.ROLE_USER);
        user.setRoles(Collections.singletonList(userRole));
        return user;
    }

    @Transactional
    Role createRoleIfNotFound(ERole name) {

        Optional<Role> existingRole = roleRepository.findByName(name);
        if (existingRole.isEmpty()) {
            Role role = new Role();
            role.setName(name);
            return roleRepository.save(role);
        }
        return existingRole.get();
    }
}
