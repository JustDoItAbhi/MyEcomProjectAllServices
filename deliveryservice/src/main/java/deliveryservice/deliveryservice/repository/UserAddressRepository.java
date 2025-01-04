package deliveryservice.deliveryservice.repository;

import deliveryservice.deliveryservice.entity.UserAddress;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress,Long> {
    Optional<UserAddress> findByUserEmail(String email);
}
