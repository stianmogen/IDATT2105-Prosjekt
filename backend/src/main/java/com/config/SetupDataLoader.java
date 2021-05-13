package com.config;

import com.common.UserPrivilege;
import com.common.UserRole;
import com.model.Privilege;
import com.model.Role;
import com.model.User;
import com.repository.PrivilegeRepository;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

      private boolean alreadySetup = false;

      @Autowired
      private PrivilegeRepository privilegeRepository;

      @Autowired
      private RoleRepository roleRepository;

      @Autowired
      private UserRepository userRepository;


      @Override
      @Transactional
      public void onApplicationEvent(ContextRefreshedEvent event) {

            if (alreadySetup)
                  return;
            Privilege readPrivilege = createPrivilegeIfNotFound(UserPrivilege.READ);
            Privilege writePrivilege = createPrivilegeIfNotFound(UserPrivilege.WRITE);
            Privilege addUserPrivilege = createPrivilegeIfNotFound(UserPrivilege.ADD_USER);

            List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege, addUserPrivilege);

            createRoleIfNotFound(UserRole.ADMIN, adminPrivileges);
            createRoleIfNotFound(UserRole.USER, Collections.singletonList(readPrivilege));

            Optional<Role> adminRole = roleRepository.findByName(UserRole.ADMIN);
            User user = new User();
            user.setFirstName("admin");
            user.setSurname("admin");
            user.setPassword("test");
            user.setEmail("test@test.com");
            adminRole.ifPresent(role -> user.setRoles(Collections.singletonList(role)));
            userRepository.save(user);

            alreadySetup = true;
      }

      @Transactional
      Privilege createPrivilegeIfNotFound(String name) {
            Optional<Privilege> existingPrivilege = privilegeRepository.findByName(name);
            if (existingPrivilege.isEmpty()) {
                  Privilege privilege = new Privilege();
                  privilege.setName(name);
                  return privilegeRepository.save(privilege);
            }
            return existingPrivilege.get();
      }

      @Transactional
      Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {

            Optional<Role> existingRole = roleRepository.findByName(name);
            if (existingRole.isEmpty()) {
                  Role role = new Role();
                  role.setName(name);
                  role.setPrivileges(privileges);
                  return roleRepository.save(role);
            }
            return existingRole.get();
      }
}
