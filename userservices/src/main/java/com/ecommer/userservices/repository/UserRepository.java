package com.ecommer.userservices.repository;

import com.ecommer.userservices.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {// USER repository
    Optional<Users> findByUserEmail(String email);// find user by its email
}
