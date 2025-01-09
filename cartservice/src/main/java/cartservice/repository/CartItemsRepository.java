package cartservice.repository;

import cartservice.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems,Long> {
    Optional<CartItems> findByProductId(long productId);

}
