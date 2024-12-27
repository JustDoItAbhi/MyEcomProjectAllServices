package cartservice.repository;


import cartservice.entity.CartItems;
import cartservice.entity.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Carts, Long> {

////    Optional<Carts> findByProductId(long productId);
}
