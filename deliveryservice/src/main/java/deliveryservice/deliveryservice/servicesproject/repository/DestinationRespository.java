package deliveryservice.deliveryservice.servicesproject.repository;

import deliveryservice.deliveryservice.servicesproject.entity.Destinations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRespository extends JpaRepository<Destinations,Long> {// DESTINATION REPOSITORY
    Destinations findByCountryAndCity(String country, String city);// FIND BY COUNTRY AND CITY
    Destinations findByCity(String city);// FIND BY CITY
}

