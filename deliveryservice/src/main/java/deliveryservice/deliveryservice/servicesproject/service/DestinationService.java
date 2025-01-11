package deliveryservice.deliveryservice.servicesproject.service;

import deliveryservice.deliveryservice.servicesproject.dto.DesignationRequestDto;
import deliveryservice.deliveryservice.servicesproject.entity.Destinations;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CityNotFound;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CountryNotFound;

import java.util.List;

public interface DestinationService {
    List<Destinations> getAll();
    Destinations getDestinationBycity(String city) throws CityNotFound;
    Destinations getDestinationByCountyandCity(String country,String city) throws CountryNotFound;
    Destinations createDesignation(DesignationRequestDto dto);
}
