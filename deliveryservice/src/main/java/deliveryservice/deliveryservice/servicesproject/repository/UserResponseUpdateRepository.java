package deliveryservice.deliveryservice.servicesproject.repository;

import deliveryservice.deliveryservice.servicesproject.entity.UserResponseUpdatedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserResponseUpdateRepository extends JpaRepository<UserResponseUpdatedEntity,Long> {
    UserResponseUpdatedEntity findByUpadatedUserEmail(String email);
}
