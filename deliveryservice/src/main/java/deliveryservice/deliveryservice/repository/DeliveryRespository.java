package deliveryservice.deliveryservice.repository;

import deliveryservice.deliveryservice.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRespository extends JpaRepository<Delivery,Long> {
}
