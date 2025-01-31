package deliveryservice.deliveryservice.servicesproject.repository;

import deliveryservice.deliveryservice.servicesproject.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress,Long> {// USER ADDRESS ENTITY  REPOSITORY
    Optional<UserAddress> findByUserEmail(String email);// FIND BY USER EMAIL
}
