package cartservice.repository;


import cartservice.entity.CartItems;
import cartservice.entity.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Carts, Long> {// CART REPOSITORY
    Optional<Carts> findByEmail(String email);// FIND CART BY USER EMAIL

}
