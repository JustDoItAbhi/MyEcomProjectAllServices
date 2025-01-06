package deliveryservice.deliveryservice.service;

import deliveryservice.deliveryservice.dto.DesignationRequestDto;
import deliveryservice.deliveryservice.entity.Destinations;
import deliveryservice.deliveryservice.exceptions.CityNotFound;
import deliveryservice.deliveryservice.exceptions.CountryNotFound;

import java.util.List;

public interface DestinationService {
    List<Destinations> getAll();
    Destinations getDestinationBycity(String city) throws CityNotFound;
    Destinations getDestinationByCountyandCity(String country,String city) throws CountryNotFound;
    Destinations createDesignation(DesignationRequestDto dto);
}
