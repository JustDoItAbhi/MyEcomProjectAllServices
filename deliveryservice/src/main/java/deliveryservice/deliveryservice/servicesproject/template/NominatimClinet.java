package deliveryservice.deliveryservice.servicesproject.template;

import deliveryservice.deliveryservice.servicesproject.dtos.Coordinates;
import deliveryservice.deliveryservice.servicesproject.dtos.NominatimResponseDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NominatimClinet {
    private Double WAREHOUSE_LAT = 49.83826;// location of warehouse
    private Double WAREHOUSE_LON =	24.02324;// location of warehouse

    private final RestTemplateBuilder restTemplateBuilder;
    private final String NOMINATIM_BASE_URL = "https://nominatim.openstreetmap.org";// DEFAULT LOCATION TRACE LINK
    // CONTRUCTOR
    public NominatimClinet(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    // Forward Geocoding: Get coordinates from an address
    public Coordinates forwardGeocode(String city, String country) {
        RestTemplate restTemplate=restTemplateBuilder.build();
        NominatimResponseDto responseDto=new NominatimResponseDto();
        String url= NOMINATIM_BASE_URL + "/search?q=" + city + "," + country + "&format=json&limit=1";

        ResponseEntity<NominatimResponseDto[]> response= restTemplate.getForEntity(url, NominatimResponseDto[].class);

//    NominatimResponseDto[]responseBody=response.getBody();
        NominatimResponseDto dto=response.getBody()[0];
        if (response.getBody() != null && response.getBody().length > 0) {
            return new Coordinates(Double.parseDouble(dto.getLat()),Double.parseDouble(dto.getLon())); // Return the first match
        } else {
            throw new RuntimeException("No results found for the given "+city+" and "+ country); // Handle this appropriately
        }
    }
    // Calculate distance using Haversine formula
    public double calculateDistance(String city,String country) {
        double R = 6371; // Earth's radius in km

        Coordinates coordinates  =forwardGeocode(city,country);

        double lat1Rad = Math.toRadians(WAREHOUSE_LAT); // Lviv warehouse latitude
        double lat2Rad = Math.toRadians(coordinates.getLatitude());// lviv warehouse latitude
        double deltaLat = Math.toRadians(coordinates.getLatitude() - WAREHOUSE_LAT);//subtract latitude to get distance
        double deltaLon = Math.toRadians(coordinates.getLongitude() - WAREHOUSE_LON);// subtract longitude to get distance

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)// logic to calculate
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
        double ans=R*c;
        return ((Math.round(ans*100)/100)); // Distance in kilometers
    }

}
