package com.ecommer.userservices.security.auth2server.authrepository;

import com.ecommer.userservices.security.auth_entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {// client repository
    Optional<Client> findByClientId(String clientId);
}
