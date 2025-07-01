package deliveryservice.deliveryservice.servicesproject.repository;

import deliveryservice.deliveryservice.servicesproject.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryRespository extends JpaRepository<Delivery,Long> {// DELIVERY REPOSITORY

    @Override
    Optional<Delivery> findById(Long id);
}
