package com.factories;

import com.common.UserPrivilege;
import com.common.UserRole;
import com.model.Privilege;
import com.model.Role;
import com.model.User;
import com.repository.PrivilegeRepository;
import com.repository.RoleRepository;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.utils.StringRandomizer.getRandomEmail;
import static com.utils.StringRandomizer.getRandomString;



public class UserFactory implements FactoryBean<User> {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public User getObject() throws Exception {

        return User.builder()
                .id(UUID.randomUUID())
                .email(getRandomEmail())
                .firstName(getRandomString(5))
                .surname(getRandomString(5)+"@email.com")
                .password(getRandomString(3)+"FactoryPassword123")
                .build();
    }


    public User setAdminRole(User user) {

        Privilege readPrivilege = createPrivilegeIfNotFound(UserPrivilege.READ);
        Privilege writePrivilege = createPrivilegeIfNotFound(UserPrivilege.WRITE);
        Privilege addUserPrivilege = createPrivilegeIfNotFound(UserPrivilege.ADD_USER);

        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege, addUserPrivilege);

        createRoleIfNotFound(UserRole.ADMIN, adminPrivileges);
        createRoleIfNotFound(UserRole.USER, Collections.singletonList(readPrivilege));
        Optional<Role> adminRole = roleRepository.findByName(UserRole.ADMIN);

        adminRole.ifPresent(role -> user.setRoles(Collections.singletonList(role)));
        return user;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
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
