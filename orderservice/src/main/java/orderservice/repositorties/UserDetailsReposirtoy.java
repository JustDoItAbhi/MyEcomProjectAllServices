package orderservice.repositorties;


import orderservice.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsReposirtoy extends JpaRepository<UserDetails,Long> {// USERDETAILS REPOSITORY
    Optional<UserDetails> findByUserEmail(String userEmail);// FIND USER BY EMAIL

}
