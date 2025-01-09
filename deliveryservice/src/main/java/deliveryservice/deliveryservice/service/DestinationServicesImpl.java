package deliveryservice.deliveryservice.service;

import deliveryservice.deliveryservice.dto.DesignationRequestDto;
import deliveryservice.deliveryservice.entity.Destinations;
import deliveryservice.deliveryservice.exceptions.exceptionfiles.CityNotFound;
import deliveryservice.deliveryservice.exceptions.exceptionfiles.CountryNotFound;
import deliveryservice.deliveryservice.repository.DestinationRespository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationServicesImpl implements DestinationService{
    private DestinationRespository destinationRespository;

    public DestinationServicesImpl(DestinationRespository destinationRespository) {
        this.destinationRespository = destinationRespository;
    }

    @Override
    public List<Destinations> getAll() {
        return destinationRespository.findAll();
    }
    @Override
    public Destinations getDestinationBycity(String city) throws CityNotFound {
        Destinations destinations = destinationRespository.findByCity(city);
        if (destinations == null) {
            throw new CityNotFound("CITY NOT FOUND " + city);
        }
//        List<Destinations>destinationsList=destinationRespository.findAll();
        return destinations;
    }

    @Override
    public Destinations getDestinationByCountyandCity(String country,String city) throws CountryNotFound {
        // Assuming findByCountry returns a List<Destinations>
        Destinations destinations = destinationRespository.findByCountryAndCity(country,city );

        if (destinations == null ) {
            throw new CountryNotFound("COUNTRY NOT FOUND: " + country);
        }

        return destinations;
    }

    @Override
    public Destinations createDesignation(DesignationRequestDto dto) {

        return null;
    }
}
