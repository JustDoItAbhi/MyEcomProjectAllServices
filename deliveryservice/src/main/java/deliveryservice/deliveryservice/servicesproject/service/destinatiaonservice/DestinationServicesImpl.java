package deliveryservice.deliveryservice.servicesproject.service.destinatiaonservice;

import deliveryservice.deliveryservice.servicesproject.dto.requests.DestinationRequestDto;
import deliveryservice.deliveryservice.servicesproject.entity.Destinations;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CityNotFound;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CountryNotFound;
import deliveryservice.deliveryservice.servicesproject.repository.DestinationRespository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationServicesImpl implements DestinationService{// DESTINATION SERVICE IMPLEMENTATION
    private final DestinationRespository destinationRespository;// DECLARED DESTINATION REPOSITORY
// CONSTRUCTOR
    public DestinationServicesImpl(DestinationRespository destinationRespository) {
        this.destinationRespository = destinationRespository;
    }

    @Override// GET ALL CITIES AND COUNTRIES
    public List<Destinations> getAll() {
        return destinationRespository.findAll();
    }
    @Override// GET CITY  BY NAME
    public Destinations getDestinationBycity(String city) throws CityNotFound {
        Destinations destinations = destinationRespository.findByCity(city);
        if (destinations == null) {
            throw new CityNotFound("CITY NOT FOUND " + city);
        }
        return destinations;
    }

    @Override// GET DESTINATION BY COUNTRY AND CITY NAME
    public Destinations getDestinationByCountyandCity(String country,String city) throws CountryNotFound {
        Destinations destinations = destinationRespository.findByCountryAndCity(country,city );// FIND BY CITY AND COUNTRY NAME
        if (destinations == null ) {// VALIDATE OBJECT
            throw new CountryNotFound("COUNTRY NOT FOUND: " + country);
        }
        return destinations;
    }

    @Override// NOT IMPLEMENTED
    public Destinations createDesignation(DestinationRequestDto dto) {
        return null;
    }
}
