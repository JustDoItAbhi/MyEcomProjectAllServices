package deliveryservice.deliveryservice.servicesproject.repository;

import deliveryservice.deliveryservice.servicesproject.entity.Destinations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRespository extends JpaRepository<Destinations,Long> {
    Destinations findByCountryAndCity(String country, String city);
    Destinations findByCity(String city);
}

