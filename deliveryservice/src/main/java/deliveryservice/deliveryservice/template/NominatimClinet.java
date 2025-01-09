package deliveryservice.deliveryservice.template;

import deliveryservice.deliveryservice.dto.Coordinates;
import deliveryservice.deliveryservice.dto.NominatimResponseDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NominatimClinet {
    private Double LVIV_LAT = 49.83826;// location of warehouse
    private Double LVIV_LON=	24.02324;// location of warehouse

    private final RestTemplateBuilder restTemplateBuilder;
    private final String NOMINATIM_BASE_URL = "https://nominatim.openstreetmap.org";

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

        double lat1Rad = Math.toRadians(LVIV_LAT); // Lviv latitude
        double lat2Rad = Math.toRadians(coordinates.getLatitude());
        double deltaLat = Math.toRadians(coordinates.getLatitude() - LVIV_LAT);
        double deltaLon = Math.toRadians(coordinates.getLongitude() - LVIV_LON);

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
        double ans=R*c;
        return ((Math.round(ans*100)/100)); // Distance in kilometers
    }

}
