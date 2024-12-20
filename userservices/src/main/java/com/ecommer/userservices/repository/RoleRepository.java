package com.ecommer.userservices.repository;

import com.ecommer.userservices.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles,Long> {
    Optional<Roles> findByRoleType(String roles);
}
