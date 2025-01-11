package deliveryservice.deliveryservice.servicesproject.controller;

import deliveryservice.deliveryservice.servicesproject.entity.Destinations;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CityNotFound;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CountryNotFound;
import deliveryservice.deliveryservice.servicesproject.service.DestinationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Destination")
public class DestinationController {
    private DestinationService destinationService;


    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }
    @GetMapping("/")
    private ResponseEntity<List<Destinations>> getAll(){
        return ResponseEntity.ok(destinationService.getAll());
    }
    @GetMapping("/{city}")
    public ResponseEntity<Destinations>getByCity(@PathVariable("city")String city) throws CityNotFound {
        return ResponseEntity.ok(destinationService.getDestinationBycity(city));
    }
    @GetMapping("/country/{country}/{city}")
    public ResponseEntity<Destinations>getBycountry(@PathVariable("country")String country
    ,@PathVariable("city")String city) throws  CountryNotFound {
        return ResponseEntity.ok(destinationService.getDestinationByCountyandCity(country,city));
    }
}
