package com.config;

import com.common.UserPermission;
import com.common.UserRole;
import com.model.Permission;
import com.model.Role;
import com.model.User;
import com.repository.PermissionRepository;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.*;

@Slf4j
@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

      private boolean alreadySetup = false;

      @Autowired
      private PermissionRepository permissionRepository;

      @Autowired
      private RoleRepository roleRepository;

      @Autowired
      private UserRepository userRepository;


      @Override
      @Transactional
      public void onApplicationEvent(ContextRefreshedEvent event) {

            if (alreadySetup)
                  return;
            Permission readPermission = createPermissionIfNotFound(UserPermission.READ);
            Permission writePermission = createPermissionIfNotFound(UserPermission.WRITE);
            Permission addUserPermission = createPermissionIfNotFound(UserPermission.ADD_USER);

            List<Permission> adminPermissions = Arrays.asList(readPermission, writePermission, addUserPermission);

            createRoleIfNotFound(UserRole.ADMIN, adminPermissions);
            createRoleIfNotFound(UserRole.USER, Collections.singletonList(readPermission));

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
      Permission createPermissionIfNotFound(String name) {
            Optional<Permission> existingPermission = permissionRepository.findByName(name);
            if (existingPermission.isEmpty()) {
                  Permission permission = new Permission();
                  permission.setName(name);
                  return permissionRepository.save(permission);
            }
            return existingPermission.get();
      }

      @Transactional
      Role createRoleIfNotFound(String name, Collection<Permission> permissions) {

            Optional<Role> existingRole = roleRepository.findByName(name);
            if (existingRole.isEmpty()) {
                  Role role = new Role();
                  role.setName(name);
                  role.setPermissions(permissions);
                  return roleRepository.save(role);
            }
            return existingRole.get();
      }
}
