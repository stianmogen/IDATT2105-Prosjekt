package com.utils;

import com.common.UserPrivilege;
import com.common.UserRole;
import com.model.Privilege;
import com.model.Role;
import com.model.User;
import com.repository.PrivilegeRepository;
import com.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Component
public class RoleUtil {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User setRoleToAdmin(User user) {
        Privilege readPrivilege = createPrivilegeIfNotFound(UserPrivilege.READ);
        Privilege writePrivilege = createPrivilegeIfNotFound(UserPrivilege.WRITE);
        Privilege addUserPrivilege = createPrivilegeIfNotFound(UserPrivilege.ADD_USER);

        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege, addUserPrivilege);

        createRoleIfNotFound(UserRole.ADMIN, adminPrivileges);

        Optional<Role> adminRole = roleRepository.findByName(UserRole.ADMIN);
        adminRole.ifPresent(role -> user.setRoles(Collections.singletonList(role)));
        return user;
    }

    public User setRoleToUser(User user) {
        Privilege readPrivilege = createPrivilegeIfNotFound(UserPrivilege.READ);
        Privilege writePrivilege = createPrivilegeIfNotFound(UserPrivilege.WRITE);

        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);

        createRoleIfNotFound(UserRole.USER, Collections.singletonList(readPrivilege));

        Optional<Role> adminRole = roleRepository.findByName(UserRole.USER);
        adminRole.ifPresent(role -> user.setRoles(Collections.singletonList(role)));
        return user;
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
