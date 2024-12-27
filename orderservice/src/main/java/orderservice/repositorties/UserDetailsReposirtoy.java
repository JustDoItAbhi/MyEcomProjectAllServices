package orderservice.repositorties;


import orderservice.users.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsReposirtoy extends JpaRepository<UserDetails,Long> {
    Optional<UserDetails> findByUserEmail(String userEmail);
    void deleteByUserEmail(String userEmail);
}
