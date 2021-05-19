package com.config;

import com.model.ERole;
import com.model.Role;
import com.model.User;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

      private boolean alreadySetup = false;

      @Autowired
      private BCryptPasswordEncoder passwordEncoder;

      @Autowired
      private RoleRepository roleRepository;

      @Autowired
      private UserRepository userRepository;


      @Override
      @Transactional
      public void onApplicationEvent(ContextRefreshedEvent event) {

            if (alreadySetup)
                  return;

            Role adminRole = createRoleIfNotFound(ERole.ROLE_ADMIN);
            Role userRole = createRoleIfNotFound(ERole.ROLE_USER);
            Role moderatorRole = createRoleIfNotFound(ERole.ROLE_MODERATOR);
            User admin = new User();
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin");
            admin.setRoles(Collections.singletonList(adminRole));
            userRepository.save(admin);

            User moderator = new User();
            moderator.setPassword(passwordEncoder.encode("moderator"));
            moderator.setEmail("moderator");
            moderator.setRoles(Collections.singletonList(moderatorRole));
            userRepository.save(moderator);

            User user = new User();
            user.setPassword(passwordEncoder.encode("user"));
            user.setEmail("user");
            user.setRoles(Collections.singletonList(userRole));
            userRepository.save(user);

            alreadySetup = true;
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
