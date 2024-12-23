package paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paymentservice.entity.Payments;
@Repository
public interface PaymentRepository extends JpaRepository<Payments,Long> {
}
