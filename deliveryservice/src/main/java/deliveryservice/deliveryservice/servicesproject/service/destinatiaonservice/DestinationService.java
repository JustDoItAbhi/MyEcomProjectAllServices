package deliveryservice.deliveryservice.servicesproject.service.destinatiaonservice;

import deliveryservice.deliveryservice.servicesproject.dtos.requests.DestinationRequestDto;
import deliveryservice.deliveryservice.servicesproject.entity.Destinations;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CityNotFound;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CountryNotFound;

import java.util.List;

public interface DestinationService {// STRATRGY AND LAYER PATTERN FOR DESTINATION SERVICE
    List<Destinations> getAll();// GET ALL CITIS ND COUNTRIES
    Destinations getDestinationBycity(String city) throws CityNotFound;// GET CITY BY NAME
    Destinations getDestinationByCountyandCity(String country,String city) throws CountryNotFound;// GET COUNTRY AND CITY BY NAME
    Destinations createDesignation(DestinationRequestDto dto);// CREATE DESTINATION
}
