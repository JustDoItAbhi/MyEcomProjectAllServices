package orderservice.repositorties;

import orderservice.entity.OrderItems;
import orderservice.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {// ORDER REPOSITORY
//    Orders findByUserId(String userId);
}
