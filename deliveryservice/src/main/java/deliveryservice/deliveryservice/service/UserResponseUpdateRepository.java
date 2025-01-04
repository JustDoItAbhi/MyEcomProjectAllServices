package deliveryservice.deliveryservice.service;

import deliveryservice.deliveryservice.entity.UserResponseUpdatedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserResponseUpdateRepository extends JpaRepository<UserResponseUpdatedEntity,Long> {
    UserResponseUpdatedEntity findByUpadatedUserEmail(String email);
}
