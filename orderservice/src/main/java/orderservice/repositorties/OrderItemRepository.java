package orderservice.repositorties;

import orderservice.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems , Long> {// ORDER ITEM REPOSITORY
    Optional<OrderItems> findByOrderItemId(String orderId);
}
