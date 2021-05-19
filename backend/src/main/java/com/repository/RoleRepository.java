package com.repository;

import com.model.ERole;
import com.model.Role;
import com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
      Optional<Role> findByName(ERole name);
}
